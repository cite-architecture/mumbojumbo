package edu.holycross.shot.mumbojumbo

import static org.junit.Assert.*
import org.junit.Test
import org.custommonkey.xmlunit.*
import java.nio.file.*

import edu.harvard.chs.cite.CtsUrn
import edu.harvard.chs.cite.CiteUrn


class TestSerializations extends GroovyTestCase {



   @Test void testTesting() {

		 assert true;

     assert shouldFail {
       assert false;
     }
   }

	@Test void testSerializeToCollection(){

    // Path to orca-config
    String orcainventory = "/cite/lib/mumbojumbo/testdata/orcaConf.tsv"
    // Path to directory where .tsv or .csv files for ORCA analyses are
    String orcafiles = "/cite/lib/mumbojumbo/testdata/orcaFiles"
    String outputdir = "build/test_output"

    OrcaArchive oa = new OrcaArchive(orcainventory, orcafiles, outputdir)

		assert oa.orcaCollections[0].getClass().toString() == "class edu.holycross.shot.mumbojumbo.OrcaCollection"

		oa.orcaCollections.each{ oc ->
				oa.serialize(oc)
		}
	}


  @Test
  void testCollectionInvFrag1(){
    // set up XMLUnit
		XMLUnit.setNormalizeWhitespace(true)

    //Set up params
		 CiteUrn urn = new CiteUrn("urn:cite:hmt:clauseAnalysis")
		 String version = "v1"
		 File of = new File("testdata/orcaFiles/clauses.csv")
		 CtsUrn vurn = new CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:")
		 String eid = "clause"
		 String sUrl = "http://localhost:8080/sparqlcts/api?"
		 String desc = "Iliad, Perseus ed., analyzed by grammatical clauses"

		OrcaCollection oc = new OrcaCollection(urn,version,vurn,of,eid,sUrl,desc)

    String outputDirName = "build/test_output"


		Path outputDir = Paths.get(outputDirName)

		// If this.outputDir doesn't exists, make it
		if ( Files.exists(outputDir)){
		} else {
			new File("${outputDir}").mkdirs()
		}

		OrcaSerializer os = new OrcaSerializer(oc, outputDir)

		os.serializeCollectionInventory()
		os.closeFiles()

		String collInvFileName = "collInv_" + oc.collectionUrn.toString().replaceAll(":","_") + '.xml'
		String fileToReadStr = "${outputDir}/${collInvFileName}"
		File fileToRead = new File(fileToReadStr)

	  String replyString = fileToRead.text

    String expectedXml = """
		<citeCollection canonicalId="URN" label="TextDeformation" urn="urn:cite:hmt:clauseAnalysis">
		        <namespaceMapping abbr="hmt" uri="http://www.homermultitext.org/datans"/>
		        <extendedBy extension="cite:ORCA"/>
		        <description xmlns="http://purl.org/dc/elements/1.1/">Iliad, Perseus ed., analyzed by grammatical clauses</description>
		        <rights xmlns="http://purl.org/dc/elements/1.1/"> All data in this collection are available
		            under the terms of the Creative Commons Attribution-Non-Commercial 3.0 Unported License,
		            http://creativecommons.org/licenses/by-nc/3.0/deed.en_US</rights>

		        <orderedBy property="Sequence"/>
		        <source type="file" value="clauses.csv"/>

		        <citeProperty name="URN" label="The URN for this association of text with analysis" type="citeurn"></citeProperty>
		        <citeProperty name="Sequence" label="Sequence" type="number"></citeProperty>
		        <citeProperty name="AnalyzedText" label="The URN to the passage of text analyzed" type="ctsurn"></citeProperty>
		        <citeProperty name="AnalysisDataUrn" label="The URN pointing to the analysis of this text" type="citeurn"></citeProperty>
		        <citeProperty name="TextDeformation" label="A presentational string resulting from applying this analysis to this passage of text" type="string"></citeProperty>

		    </citeCollection>
				"""

		  Diff xmlDiff = new Diff(expectedXml, replyString)
		  assert xmlDiff.identical()
  }

  @Test
  void testTextInvFrag1(){
    // set up XMLUnit
		XMLUnit.setNormalizeWhitespace(true)

    //Set up params
		 CiteUrn urn = new CiteUrn("urn:cite:hmt:clauseAnalysis")
		 String version = "v1"
		 File of = new File("testdata/orcaFiles/clauses.csv")
		 CtsUrn vurn = new CtsUrn("urn:cts:greekLit:tlg0012.tlg001.fuPers:")
		 String eid = "clauses"
		 String sUrl = "http://localhost:8080/sparqlcts/api?"
		 String desc = "Iliad, Perseus ed., analyzed by grammatical clauses"

		OrcaCollection oc = new OrcaCollection(urn,version,vurn,of,eid,sUrl,desc)

    String outputDirName = "build/test_output"


		Path outputDir = Paths.get(outputDirName)

		// If this.outputDir doesn't exists, make it
		if ( Files.exists(outputDir)){
		} else {
			new File("${outputDir}").mkdirs()
		}

		OrcaSerializer os = new OrcaSerializer(oc, outputDir)

		os.serializeCtsInventory()
		os.closeFiles()

		String aeTiFileName = "ti_" + oc.collectionUrn.toString().replaceAll(":","_") + '.xml'
		String fileToReadStr = "${outputDir}/${aeTiFileName}"
		File fileToRead = new File(fileToReadStr)

	  String replyString = fileToRead.text

    String expectedXml = """
		<frag>
			<!-- Paste into the edition/translation element urn:cts:greekLit:tlg0012.tlg001.fuPers: -->
			<exemplar urn="urn:cts:greekLit:tlg0012.tlg001.fuPers.clauses:">
			 <label xml:lang="eng">Analytical Exemplar:	Iliad, Perseus ed., analyzed by grammatical clauses</label>
	    </exemplar>
			<!-- Pase into citationconfig.xml -->
			<online urn="urn:cts:greekLit:tlg0012.tlg001.fuPers.clauses:" type="82xf" docname="exemplar_urn_cite_hmt_clauseAnalysis.txt" nodeformat="text">
					<!-- YOU MUST CUSTOMIZE THE CITATION INFO!
	        <citationScheme>
	            <citation label="entry">
	                <citation label="section"/>
	            </citation>
	        </citationScheme>
					-->
	    </online>
		</frag>
		"""

		  Diff xmlDiff = new Diff(expectedXml, replyString)
		  assert xmlDiff.identical()
  }
}

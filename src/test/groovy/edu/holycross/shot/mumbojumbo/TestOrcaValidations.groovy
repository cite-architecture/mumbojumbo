package edu.holycross.shot.mumbojumbo

import edu.harvard.chs.cite.CtsUrn
import edu.harvard.chs.cite.CiteUrn

import au.com.bytecode.opencsv.CSVReader
import java.nio.file.*

import static org.junit.Assert.*
import org.junit.Test

class TestOrcaValidations extends GroovyTestCase {



   @Test void testTesting() {

		 assert true;

     assert shouldFail {
       assert false;
     }
   }



	 @Test void testGoodOrcaLine(){
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

		 String[] testLine = ["urn:cts:greekLit:tlg0012.tlg001.msA:9.421@Ἀχαιῶν[1]","urn:cite:hmt:place.place96","Ἀχαιῶν"]

		 assert testLine.size() == 3

		 assert os.validateOrcaLine(testLine)
	 }

	 @Test void testBadOrcaLine1(){
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

		 String[] testLine = ["urn:cts:greekLit:tlg0012.tlg001.msA:9.421@Ἀχαιῶν[1]","urn:cite:hmt:place.place96"]

		 assert shouldFail{
			 assert os.validateOrcaLine(testLine)
		 }
	 }


	 @Test void testBadOrcaLine2(){
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

		 String[] testLine = ["urn:notCts:greekLit:tlg0012.tlg001.msA:9.421@Ἀχαιῶν[1]","urn:cite:hmt:place.place96","Ἀχαιῶν"]

		 assert shouldFail{
			 assert os.validateOrcaLine(testLine)
		 }
	 }

	 @Test void testBadOrcaLine3(){
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

		 String[] testLine = ["urn:cts:greekLit:tlg0012.tlg001.msA:9.421@Ἀχαιῶν[1]","urn:not-a-cite-urn","Ἀχαιῶν"]

		 assert shouldFail{
			 assert os.validateOrcaLine(testLine)
		 }
	 }

	 @Test void testCtsUrns1(){
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

		 String[] testLine = ["urn:cts:greekLit:tlg0012.tlg001.msA:9.421@Ἀχαιῶν[1]","urn:cite:hmt:place.place96","Ἀχαιῶν"]


		 assert os.oc.validateExemplarIntegrity(os.oc.textUrn, testLine)
	 }

	 @Test void testCtsUrns2(){
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

		 String[] testLine = ["urn:cts:greekLit:tlg0012.tlg001.XXX:9.421@Ἀχαιῶν[1]","urn:cite:hmt:place.place96","Ἀχαιῶν"]


		 assert shouldFail{
		 assert os.oc.validateExemplarIntegrity(os.oc.textUrn, testLine)
		 }
	 }


}

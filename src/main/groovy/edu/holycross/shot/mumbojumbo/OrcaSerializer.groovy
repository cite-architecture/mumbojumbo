
package edu.holycross.shot.mumbojumbo

import edu.harvard.chs.cite.CiteUrn
import edu.harvard.chs.cite.CtsUrn
import au.com.bytecode.opencsv.CSVReader
import java.nio.file.*


class OrcaSerializer {

		Path outputDir
		OrcaCollection oc
		OutputStreamWriter aeOut
		String aeOutFilename
		OutputStreamWriter aeTiOut
		OutputStreamWriter idxOut
		OutputStreamWriter idxInvOut
		OutputStreamWriter collInvOut
		OutputStreamWriter collOut


  /** constructor taking an OrcaCollection
	 * tries to initialize six output files
   * @param OrcaCollection
	   @param Path outputDir
   * @throws exception if any file can't be opened as an OutputStreamWriter
   */
	OrcaSerializer(OrcaCollection oc, Path outputDir)
		throws Exception{

			this.outputDir = outputDir
			this.oc = oc

			//File path+name for collection data
			String collFileName = this.oc.collectionUrn.toString().replaceAll(":","_") + '.tsv'
			String collOutString = "${this.outputDir}/${collFileName}"
			this.collOut = new OutputStreamWriter(new FileOutputStream(collOutString), "UTF-8")
			this.collOut.write("")

			//File path+name for collectionInventoryFragment
			String collInvFileName = "collInv_" + this.oc.collectionUrn.toString().replaceAll(":","_") + '.xml'
			String collInvOutString = "${this.outputDir}/${collInvFileName}"
			this.collInvOut = new OutputStreamWriter(new FileOutputStream(collInvOutString), "UTF-8")
			this.collInvOut.write("")

			//File path+name for analytical exemplar
			String aeFileName = "exemplar_" + this.oc.collectionUrn.toString().replaceAll(":","_") + '.txt'
			String aeOutString = "${this.outputDir}/${aeFileName}"
			this.aeOutFilename = aeFileName // we need this later
			this.aeOut = new OutputStreamWriter(new FileOutputStream(aeOutString), "UTF-8")
			this.aeOut.write("")
			this.aeOut.append("URN#Previous#Sequence#Next#Text\n")

			//File path+name for cts text inventory fragment
			String aeTiFileName = "ti_" + this.oc.collectionUrn.toString().replaceAll(":","_") + '.xml'
			String aeTiOutString = "${this.outputDir}/${aeTiFileName}"
			this.aeTiOut = new OutputStreamWriter(new FileOutputStream(aeTiOutString), "UTF-8")
			this.aeTiOut.write("")

			//File path+name for CITE Index for the orca:exemplifies relationship
			String idxFileName = "idx_" + this.oc.collectionUrn.toString().replaceAll(":","_") + '.tsv'
			String idxOutString = "${this.outputDir}/${idxFileName}"
			this.idxOut = new OutputStreamWriter(new FileOutputStream(idxOutString), "UTF-8")
			this.idxOut.write("")

			//File path+name for CITE Index Inventory for the orca:exemplifies relationship
			String idxInvFileName = "idxInv_" + this.oc.collectionUrn.toString().replaceAll(":","_") + '.tsv'
			String idxInvOutString = "${this.outputDir}/${idxInvFileName}"
			this.idxInvOut = new OutputStreamWriter(new FileOutputStream(idxInvOutString), "UTF-8")
			this.idxInvOut.write("")

		}

	void closeFiles()
		throws Exception {

			try{
			this.aeOut.close()
			this.idxOut.close()
			this.idxInvOut.close()
			this.aeTiOut.close()
			this.collInvOut.close()
	    this.collOut.close()
		} catch (Exception e){
			throw new Exception("OrcaSerializer: ${e}")
		}
	}

	void writeAnalysis(String[] tabbedLine){
		System.err.println("Would write '${tabbedLine}'.")
	}

	void writeExemplar(){
			System.err.printl("Would write exemplar for ${}.")
	}

	void serializeCollectionInventory(){
		this.collInvOut.append("""<citeCollection canonicalId="URN" label="TextDeformation" urn="${this.oc.collectionUrn}">""")
		this.collInvOut.append("""
		        <namespaceMapping abbr="hmt" uri="http://www.homermultitext.org/datans"/>
		        <extendedBy extension="cite:ORCA"/>
		        <description xmlns="http://purl.org/dc/elements/1.1/">${this.oc.description}</description>
		""")
		this.collInvOut.append("""
		        <rights xmlns="http://purl.org/dc/elements/1.1/"> All data in this collection are available
		            under the terms of the Creative Commons Attribution-Non-Commercial 3.0 Unported License,
		            http://creativecommons.org/licenses/by-nc/3.0/deed.en_US</rights>

		        <orderedBy property="Sequence"/>
		""")
		this.collInvOut.append("""
		        <source type="file" value="${this.oc.orcaFile.getName()}"/>
		""")
		this.collInvOut.append("""
		        <citeProperty name="URN" label="The URN for this association of text with analysis" type="citeurn"></citeProperty>
		        <citeProperty name="Sequence" label="Sequence" type="number"></citeProperty>
		        <citeProperty name="AnalyzedText" label="The URN to the passage of text analyzed" type="ctsurn"></citeProperty>
		        <citeProperty name="AnalysisDataUrn" label="The URN pointing to the analysis of this text" type="citeurn"></citeProperty>
		        <citeProperty name="TextDeformation" label="A presentational string resulting from applying this analysis to this passage of text" type="string"></citeProperty>

		    </citeCollection>
		""")
		this.collInvOut.close()
	}

	void serializeIndexInventory(){
		System.err.println("Would serialize Index Fragment for '${this.oc.exemplarId}'")

	}

	String serializeCtsInventory(CtsUrn exemplarUrn){
		System.err.println("Would serialize Text Inventory Fragment for '${this.oc.exemplarId}'")
		this.aeTiOut.append("""
		<frag>
			<!-- Paste into the edition/translation element ${this.oc.textUrn} -->
		""")
		this.aeTiOut.append("""
			<exemplar urn="${this.oc.exemplarUrn}">
		""")
		this.aeTiOut.append("""
			 <label xml:lang="eng">Analytical Exemplar:	${this.oc.description}</label>
	    </exemplar>
			<!-- Pase into citationconfig.xml -->
		""")
		this.aeTiOut.append("""
			<online urn="${this.oc.exemplarUrn}" type="82xf" docname="${this.aeOutFilename}" nodeformat="text">
		""")
		this.aeTiOut.append("""
					<!-- YOU MUST CUSTOMIZE THE CITATION INFO!
	        <citationScheme>
	            <citation label="entry">
	                <citation label="section"/>
	            </citation>
	        </citationScheme>
					-->
	    </online>
		</frag>
		""")
		this.aeTiOut.close()

	}

}


/**
			//File path+name for collection data
			String collFileName = oc.collectionUrn.toString().replaceAll(":","_") + '.tsv'
			String collOutString = "${this.outputDir}/${collFileName}"
			OutputStreamWriter collOut = new OutputStreamWriter(new FileOutputStream(collOutString), "UTF-8")
			collOut.write("")

			//File path+name for collectionInventoryFragment
			String collInvFileName = "collInv_" + oc.collectionUrn.toString().replaceAll(":","_") + '.tsv'
			String collInvOutString = "${this.outputDir}/${collInvFileName}"
			OutputStreamWriter collInvOut = new OutputStreamWriter(new FileOutputStream(collInvOutString), "UTF-8")
			collInvOut.write("")

			//File path+name for analytical exemplar
			String aeFileName = "exemplar_" + oc.collectionUrn.toString().replaceAll(":","_") + '.tsv'
			String aeOutString = "${this.outputDir}/${aeFileName}"
			OutputStreamWriter aeOut = new OutputStreamWriter(new FileOutputStream(aeOutString), "UTF-8")
			aeOut.write("")
			aeOut.append("URN#Previous#Sequence#Next#Text\n")

			//File path+name for cts text inventory fragment
			String aeTiFileName = "ti_" + oc.collectionUrn.toString().replaceAll(":","_") + '.tsv'
			String aeTiOutString = "${this.outputDir}/${aeTiFileName}"
			OutputStreamWriter aeTiOut = new OutputStreamWriter(new FileOutputStream(aeTiOutString), "UTF-8")
			aeTiOut.write("")

			//File path+name for CITE Index for the orca:exemplifies relationship
			String idxFileName = "idx_" + oc.collectionUrn.toString().replaceAll(":","_") + '.tsv'
			String idxOutString = "${this.outputDir}/${idxFileName}"
			OutputStreamWriter idxOut = new OutputStreamWriter(new FileOutputStream(idxOutString), "UTF-8")
			idxOut.write("")

			//File path+name for CITE Index Inventory for the orca:exemplifies relationship
			String idxInvFileName = "idxInv_" + oc.collectionUrn.toString().replaceAll(":","_") + '.tsv'
			String idxInvOutString = "${this.outputDir}/${idxInvFileName}"
			OutputStreamWriter idxInvOut = new OutputStreamWriter(new FileOutputStream(idxInvOutString), "UTF-8")
			idxInvOut.write("")

			// Holding variables for CITE Collections
			CiteUrn cite_orcaRelationUrn
			CtsUrn cite_analyzedText
			Number cite_orcaSeq
			CiteUrn cite_analysisUrn
			String cite_textDeformation // ***Sanitize LINE-BREAKs and TABs!
			CtsUrn cite_analyticalExemplarUrn

			// Holding variables for CTS Text
		  Number cts_sequence
			CtsUrn cts_prev
			CtsUrn cts_next
**/

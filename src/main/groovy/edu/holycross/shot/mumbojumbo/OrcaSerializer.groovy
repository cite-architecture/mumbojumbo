
package edu.holycross.shot.mumbojumbo

import edu.harvard.chs.cite.CiteUrn
import edu.harvard.chs.cite.CtsUrn
import au.com.bytecode.opencsv.CSVReader
import java.nio.file.*


class OrcaSerializer {

		Path outputDir
		OrcaCollection oc
		OutputStreamWriter aeOut
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


			System.err.println("Construction OrcaSerializer")

			//File path+name for collection data
			String collFileName = this.oc.collectionUrn.toString().replaceAll(":","_") + '.tsv'
			String collOutString = "${this.outputDir}/${collFileName}"
			this.collOut = new OutputStreamWriter(new FileOutputStream(collOutString), "UTF-8")
			this.collOut.write("")

			//File path+name for collectionInventoryFragment
			String collInvFileName = "collInv_" + this.oc.collectionUrn.toString().replaceAll(":","_") + '.tsv'
			String collInvOutString = "${this.outputDir}/${collInvFileName}"
			this.collInvOut = new OutputStreamWriter(new FileOutputStream(collInvOutString), "UTF-8")
			this.collInvOut.write("")

			//File path+name for analytical exemplar
			String aeFileName = "exemplar_" + this.oc.collectionUrn.toString().replaceAll(":","_") + '.tsv'
			String aeOutString = "${this.outputDir}/${aeFileName}"
			this.aeOut = new OutputStreamWriter(new FileOutputStream(aeOutString), "UTF-8")
			this.aeOut.write("")
			this.aeOut.append("URN#Previous#Sequence#Next#Text\n")

			//File path+name for cts text inventory fragment
			String aeTiFileName = "ti_" + this.oc.collectionUrn.toString().replaceAll(":","_") + '.tsv'
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

	String serializeCollectionInventory(){
		return "collection-xml"
	}

	String serializeIndexInventory(){
		return "index-xml"
	}

	String serializeCtsInventory(CtsUrn exemplarUrn){
		return "index-xml"
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

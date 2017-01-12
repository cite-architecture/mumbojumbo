
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
		String collOutFilename


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
			this.collOutFilename = collOutString
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

	Boolean validateCollection()
		throws Exception {
			try{
				Boolean returnVal = false
				if( this.oc.orcaFile.getName().contains(".csv")){
					 returnVal = validateCsvCollection()
				} else if (this.oc.orcaFile.getName().contains(".tsv")){
					returnVal = validateTsvCollection()
				} else {
					throw new Exception("OrcaSerializer: orca file must be .tsv or csv.")
				}
			} catch(Exception e){
					throw new Exception("OrcaSerializer validateCollection error: ${e}")
			}
	}

	Boolean validateTsvCollection(){
			// Get the ORCA file,do a pass to check integrity and grab the CTS stuff
			// Check:
			// - all lines have three columns
			// - the first column is a well-formed CTS URN
			// - the second is a well-formed CITE URN
			// - all the CTS URNs are identical up to the Version level
			CtsUrn cts_analyticalExemplarUrn
			CiteUrn cite_analysisUrn
			CtsUrn cts_testUrn
			this.oc.orcaFile.eachLine { ln, i ->
				if( ln.tokenize("\t").size() != 3 ){
					throw new Exception("Orca file must have three columns: AnalyzedText, AnalysisURN, textDeformation")
				}
				if( i > 1 ){ // skip header
					try{
						Boolean isValid = validateOrcaLine(ln.tokenize("\t") as String[])
					} catch (Exception e){
						closeFiles()
						System.err.println("OrcaArchive. Failed validation at line ${i}: ${ln} (Error: " + e + ")")
						throw new Exception("OrcaArchive. Failed validation at line ${i}: ${ln} (Error: " + e + ")")
					}
				}

				// Grab the first CTS URN and make sure all subsequent CTS URNs are identical to the Version level
				if (i > 1 ){ // skip header row
					Boolean isValid = this.oc.validateExemplarIntegrity(this.oc.textUrn, ln.tokenize("\t") as String[])
					if( i > 1 ){
						if ( !(isValid) ){
							closeFiles()
							System.err.println("OrcaArchive. Failed exemplar citation validation at line ${i}: ${ln}")
							throw new Exception("OrcaArchive. Failed exemplar citation validation at line ${i}: ${ln}")
						}
					}
				}
			}
		return true
	}

	Boolean validateCsvCollection(){
			// Get the ORCA file,do a pass to check integrity and grab the CTS stuff
			// Check:
			// - all lines have three columns
			// - the first column is a well-formed CTS URN
			// - the second is a well-formed CITE URN
			// - all the CTS URNs are identical up to the Version level
			CtsUrn cts_analyticalExemplarUrn
			CiteUrn cite_analysisUrn
			CtsUrn cts_testUrn
			CSVReader reader = new CSVReader(new FileReader(this.oc.orcaFile))
			reader.readAll().eachWithIndex { ln, i ->
				if( ln.size() != 3 ){
					throw new Exception("Orca file must have three columns: AnalyzedText, AnalysisURN, textDeformation")
				}
				if( i > 0 ){ // skip header
					try{
						Boolean isValid = validateOrcaLine(ln)
					} catch (Exception e){
						closeFiles()
						System.err.println("OrcaArchive. Failed validation at line ${i+1}: ${ln} (Error: " + e + ")")
						throw new Exception("OrcaArchive. Failed validation at line ${i+1}: ${ln} (Error: " + e + ")")
					}
				}

				// Grab the first CTS URN and make sure all subsequent CTS URNs are identical to the Version level
				if (i > 0 ){ // skip header row
					Boolean isValid = this.oc.validateExemplarIntegrity(this.oc.textUrn, ln)
					if( i > 1 ){
						if ( !(isValid) ){
							closeFiles()
							System.err.println("OrcaArchive. Failed exemplar citation validation at line ${i+1}: ${ln}")
							throw new Exception("OrcaArchive. Failed exemmplar citation validation at line ${i+1}: ${ln}")
						}
					}
				}
			}
		return true
	}

	Boolean validateOrcaLine(String[] ln)
		throws Exception {
			CtsUrn ctsurn
			CtsUrn testurn
			CiteUrn citeurn

			Boolean returnVal = true

			try{
				 assert ln.size() == 3
			} catch (Exception e){
				returnVal = false
				throw new Exception("Incorrect number of records (should be 3): ${ln}")
			}
			try{
				ctsurn = new CtsUrn(ln[0])
			} catch (Exception e){
				returnVal = false
				throw new Exception("Invalid CtsUrn")
			}
			try{
				citeurn = new CiteUrn(ln[1])
			} catch (Exception e){
				returnVal = false
				throw new Exception("Invalid CiteUrn")
			}

			return returnVal

	}

  void serializeOrcaCollection()
		throws Exception {
			try{
				this.collOut.write("URN\tSequence\tAnalyzedText\tAnalysisDataUrn\tTextDeformation\n")
				Boolean returnVal = false
				if( this.oc.orcaFile.getName().contains(".csv")){
					System.err.println("running serializeCsvCollection on ${this.oc.orcaFile.getName()}")
					 returnVal = serializeCsvCollection()
				} else if (this.oc.orcaFile.getName().contains(".tsv")){
					System.err.println("running serializeTsvCollection on ${this.oc.orcaFile.getName()}")
					returnVal = serializeTsvCollection()
				} else {
					throw new Exception("OrcaSerializer: orca file must be .tsv or csv.")
				}
				closeFiles()
			} catch(Exception e){
					throw new Exception("OrcaSerializer serializeCollection error: ${e}")
			}
		}

	void serializeCsvCollection(){
			CSVReader reader = new CSVReader(new FileReader(this.oc.orcaFile))
			reader.readAll().eachWithIndex { ln, i -> // 0-delimited
				if (i > 0){
					String orcaUrnString = "${this.oc.collectionUrn}.${i+1}.${this.oc.versionString}"
					CiteUrn orcaUrn = new CiteUrn(orcaUrnString)
					CtsUrn atUrn = new CtsUrn(ln[0])
					CiteUrn adUrn = new CiteUrn(ln[1])
					this.collOut.append("${orcaUrn}\t${i}\t${atUrn}\t${adUrn}\t${ln[2]}\n")
				}
			}
	}

	void serializeTsvCollection(){
			CSVReader reader = new CSVReader(new FileReader(this.oc.orcaFile))
			this.oc.orcaFile.eachLine { ln, i -> // 1-delimited
				if (i > 1){
					String orcaUrnString = "${this.oc.collectionUrn}.${i}.${this.oc.versionString}"
					CiteUrn orcaUrn = new CiteUrn(orcaUrnString)
					CtsUrn atUrn = new CtsUrn(ln.tokenize("\t")[0])
					CiteUrn adUrn = new CiteUrn(ln.tokenize("\t")[1])
					this.collOut.append("${orcaUrn}\t${i-1}\t${atUrn}\t${adUrn}\t${ln.tokenize("\t")[2]}\n")
				}
			}

	}

	void writeAnalysis(String[] tabbedLine){
		System.err.println("Would write '${tabbedLine}'.")
	}

	void writeExemplar(){
			System.err.printl("Would write exemplar for ${}.")
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

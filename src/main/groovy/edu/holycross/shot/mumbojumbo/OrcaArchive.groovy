
package edu.holycross.shot.mumbojumbo

import edu.harvard.chs.cite.CiteUrn
import edu.harvard.chs.cite.CtsUrn
import au.com.bytecode.opencsv.CSVReader
import java.nio.file.*

/** Class for generating ORCA Collections.
 * The OrcaArchive uses csv or tsv files to create ORCA Collections and corresponding
 * CTS Analytical Exemplar files;
 * ORCA Collections are tsv files; CTS Analytical exemplars are written out as 82xf files.
 */

class OrcaArchive {


	File invFile
  Path sourceDir
	Path outputDir
	def orcaCollections = []



  /** Constructor taking String name of a directory.
   * @param invFileName Name of the inventory file.
   * @param sourceFilesDirName path directory with three-column ORCAs
	 * @param outputDirName path to the directory for outputting files
   * configuration files.
   * @throws Exception if files cannot be found
   */
  OrcaArchive(String invFileName, String sourceFilesDirName, String outputDirName )
  throws Exception {
    this.invFile = new File (invFileName)
    this.sourceDir = Paths.get(sourceFilesDirName)
    this.outputDir= Paths.get(outputDirName)

		// Read invFile; make an array of orcaCollection objects

		// tsv or csv?
		if (this.invFile.getName().contains(".csv")){
				orcaCollections = readCsvInventory(this.invFile, this.sourceDir)
		} else if (this.invFile.getName().contains(".tsv")){
				orcaCollections = readTsvInventory(this.invFile, this.sourceDir)
		} else {
			throw new Exception("Inventory file must be .csv or .tsv")
		}

  }

	/** Reads a csv inventory file. Returns an ArrayList
	 * of OrcaCollection objects
	 * @param file inventory file
	 * @param file directory holding orca files
	 * @returns ArrayList of orcaCollection objects
	 * @throws Exception if any of the documented files can't be found
  */

	ArrayList readCsvInventory(File invFile, Path orcaFiles)
	throws Exception {
		CiteUrn urn
		String versionString
		String fileName
		String exemplarId
		String serviceUrl
		String description
		OrcaCollection tempOC
		def returnArray = []




		CSVReader reader = new CSVReader(new FileReader(invFile))
		Integer num = 0
		reader.readAll().eachWithIndex { ln, i ->
			num++
			if (ln.size() != 6){
				throw new Exception("OrcaArchive: inventory must have six columns")
			}

			if ( i > 0){
				try{
					urn = new CiteUrn(ln[0])
				} catch (Exception e){
						throw new Exception("OrcaArcive: bad CITE URN for collection URN: ${urn}")
				}

				versionString = ln[1]
				fileName = ln[2]
				exemplarId = ln[3]
				serviceUrl = ln[4]
				description = ln[5]
				try{
					orcaFiles.eachFileMatch(ln[2]){ f ->
						tempOC = new OrcaCollection(urn,versionString,f.toFile(),exemplarId,serviceUrl,description)
						returnArray << tempOC
					}
				} catch (Exception e){
						throw new Exception("OrcaArchive: exception ${e}")
				}
			}

		}

		return returnArray

	}


	/** Reads a tsv inventory file. Returns an ArrayList
	 * of OrcaCollection objects
	 * @param file inventory file
	 * @param file directory holding orca files
	 * @returns ArrayList of orcaCollection objects
	 * @throws Exception if any of the documented files can't be found
  */

	ArrayList readTsvInventory(File invFile, Path orcaFiles)
	throws Exception {
		CiteUrn urn
		String versionString
		String fileName
		String exemplarId
		String serviceUrl
		String description
		OrcaCollection tempOC
		def returnArray = []




		Integer num = 0
		invFile.eachLine{ ln, i ->
			num++
			if (ln.tokenize("\t").size() != 6){
				throw new Exception("OrcaArchive: inventory must have six columns")
			}

			if ( i > 1){ // Why the hell is the eachLine index 1-based?
				try{
					urn = new CiteUrn(ln.tokenize("\t")[0])
				} catch (Exception e){
						throw new Exception("OrcaArcive: bad CITE URN for collection URN: ${urn}")
				}

				versionString = ln.tokenize("\t")[1]
				fileName = ln.tokenize("\t")[2]
				exemplarId = ln.tokenize("\t")[3]
				serviceUrl = ln.tokenize("\t")[4]
				description = ln.tokenize("\t")[5]
				try{
					orcaFiles.eachFileMatch(ln.tokenize("\t")[2]){ f ->
						tempOC = new OrcaCollection(urn,versionString,f.toFile(),exemplarId,serviceUrl,description)
						returnArray << tempOC
					}
				} catch (Exception e){
						throw new Exception("OrcaArchive: exception ${e}")
				}
			}

		}

		return returnArray

	}


	/** Serializes an OrcaCollection object's data to…
	* a collection .tsv file
	* an 82xf CTS file
	* a CollectionInventory fragment
	* a TextInventory fragment
	* All of this happens in one umbrella-method, since
	* we need the exemplar CTS URN when we write out the collection-object
	* @param OrcaCollection oc
	* @throws Exception if it can't open or write to its files
	*/
	void serialize(OrcaCollection oc)
	throws Exception{

		try{

			// If this.outputDir doesn't exists, make it
			if ( Files.exists(this.outputDir)){
			} else {
				new File("${this.outputDir}").mkdirs()
			}

			OrcaSerializer os = new OrcaSerializer(oc, this.outputDir)



			// Write Collection Inventory Fragment
		  // For reference…
			//		- OrcaRelationUrn
			//		- AnalyzedText
			//				verb: http://www.homermultitext.org/orca/rdf/analyzedBy
			//				inverseVerb: http://www.homermultitext.org/orca/rdf/analyzes
			//		- Sequence
			//		- AnalysisUrn
			//				verb: http://www.homermultitext.org/orca/rdf/analysisFor
			//				inverseVerb: http://www.homermultitext.org/orca/rdf/hasAnalysis
			//		- TextDeformation
			//		- AnalyticalExemplarUrn
			// Write Analytical Exemplar _and_ CITE-Indices
			// Index:
			//	<version-level CTS URN> http://www.homermultitext.org/orca/rdf/exemplifiedBy <exemp-URN>
			//	<exemp-URN>  http://www.homermultitext.org/orca/rdf/exemplifies <version-URN>
			//



			// Get the ORCA file,do a pass to check integrity and grab the CTS stuff
			CtsUrn cts_analyticalExemplarUrn
			CiteUrn cite_analysisUrn
			CSVReader reader = new CSVReader(new FileReader(oc.orcaFile))
			reader.readAll().eachWithIndex { ln, i ->
				if( ln.size() != 3 ){
					throw new Exception("Orca file must have three columns: AnalyzedText, AnalysisURN, textDeformation")
				}
				if( i > 0 ){ // skip header
					try{
						os.writeAnalysis(ln)
					} catch (Exception e){
						os.closeFiles()
							throw new Exception("OrcaArchive. Serialize: Failed to make CTS Urn. " + e)
					}
				}
			}

			os.closeFiles()

		} catch(Exception e){
			throw new Exception("OrcaArchive: Serialize exception: ${e}")
		}


	}


}

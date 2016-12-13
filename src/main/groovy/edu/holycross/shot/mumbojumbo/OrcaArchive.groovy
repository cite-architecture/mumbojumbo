
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
				System.err.println("CSV!!")
				orcaCollections = readCsvInventory(this.invFile, this.sourceDir)
		} else if (this.invFile.getName().contains(".tsv")){
				System.err.println("TSV!!")
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
		OrcaCollection tempOC
		def returnArray = []



		System.err.println("About to read file: ${invFile}")

		CSVReader reader = new CSVReader(new FileReader(invFile))
		Integer num = 0
		reader.readAll().eachWithIndex { ln, i ->
			num++
			if (ln.size() != 5){
				throw new Exception("OrcaArchive: inventory must have five columns")
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
				try{
					orcaFiles.eachFileMatch(ln[2]){ f ->
						tempOC = new OrcaCollection(urn,versionString,f.toFile(),exemplarId,serviceUrl)
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
		OrcaCollection tempOC
		def returnArray = []



		System.err.println("About to read file: ${invFile}")

		Integer num = 0
		invFile.eachLine{ ln, i ->
			System.err.println("Line: ${i}")
			System.err.println(ln)
			num++
			if (ln.tokenize("\t").size() != 5){
				throw new Exception("OrcaArchive: inventory must have five columns")
			}

			if ( i > 1){ // Why the hell is the eachLine index 1-based?
				try{
					System.err.println(ln.tokenize("\t")[0])
					System.err.println(ln.tokenize("\t")[1])
					System.err.println(ln.tokenize("\t")[2])
					System.err.println(ln.tokenize("\t")[3])
					System.err.println(ln.tokenize("\t")[4])
					urn = new CiteUrn(ln.tokenize("\t")[0])
				} catch (Exception e){
						throw new Exception("OrcaArcive: bad CITE URN for collection URN: ${urn}")
				}

				versionString = ln.tokenize("\t")[1]
				fileName = ln.tokenize("\t")[2]
				exemplarId = ln.tokenize("\t")[3]
				serviceUrl = ln.tokenize("\t")[4]
				try{
					orcaFiles.eachFileMatch(ln.tokenize("\t")[2]){ f ->
						tempOC = new OrcaCollection(urn,versionString,f.toFile(),exemplarId,serviceUrl)
						returnArray << tempOC
					}
				} catch (Exception e){
						throw new Exception("OrcaArchive: exception ${e}")
				}
			}

		}

		return returnArray

	}

}


package edu.holycross.shot.mumbojumbo

import edu.harvard.chs.cite.CiteUrn
import edu.harvard.chs.cite.CtsUrn
import au.com.bytecode.opencsv.CSVReader

/** A Class for a single Orca Collection
 */

class OrcaCollection {

	CiteUrn collectionUrn
	String versionString
	File orcaFile
	CtsUrn textUrn
	String exemplarId
	CtsUrn exemplarUrn
	String ctsServiceUrl
	String description

  /** constructor taking string name of a directory.
   * @param collectionurn a cite urn
   * @param versionstring string defining the version of objects in this collectionurn
	 * (allows versioned collections to be built independently)
	 * @param orcafile file, the name of the file that records the orca records
	 * @param exemplarid string, the exmplar-id component of an analytical exemplar cts urn
   * @throws exception if if orcafile cannot be found
   */
	OrcaCollection(CiteUrn collectionUrn, String versionString, CtsUrn textUrn, File orcaFile, String exemplarId, String ctsServiceUrl, String description)
  throws Exception {
    this.collectionUrn = collectionUrn
    this.versionString = versionString
    this.orcaFile = orcaFile
		this.textUrn = textUrn
		this.exemplarId = exemplarId
		this.ctsServiceUrl = ctsServiceUrl
		this.description = description
		try{
			this.exemplarUrn = constructExemplarUrn(textUrn,exemplarId)
		} catch (Exception e){
			throw new Exception("OrcaCollection: failed to make valid CTS URN out of ${textUrn}${exemplarId}: (Reported: ${e})")
		}


		if ( !(this.orcaFile.exists()) ){
			throw new Exception("OrcaCollection: orcaFile does not exist.")
		}


  }

	CtsUrn constructExemplarUrn(CtsUrn turn, String eid)
		throws Exception {
			CtsUrn exemplarUrn
			String newUrn
			try{
				if (turn.labelForWorkLevel() != 'version'){
					throw new Exception("textUrn must be version-level")
				}
				newUrn = "urn:cts:${turn.ctsNamespace}:${turn.textGroup}.${turn.work}.${turn.version}.${eid}:"
			  exemplarUrn = new CtsUrn(newUrn)

			} catch (Exception e){
				throw new Exception(e)
			}


			return exemplarUrn
	}

  Boolean validateExemplarIntegrity(CtsUrn testUrn, String[] ln){
			CtsUrn thisUrn
			CtsUrn thisWOPassage
			String thisVersionUrnStr
			CtsUrn testWOPassage
			String testVersionUrnStr


			Boolean returnVal
			try{
				thisUrn = new CtsUrn(ln[0])
				thisWOPassage = new CtsUrn(thisUrn.getUrnWithoutPassage())
				thisVersionUrnStr = thisWOPassage.reduceToVersion()
				testWOPassage = new CtsUrn(testUrn.getUrnWithoutPassage())
				testVersionUrnStr = testWOPassage.reduceToVersion()
				returnVal = (thisVersionUrnStr == testVersionUrnStr)
			} catch (Exception e){
				returnVal = false
				throw new Exception("OrcaArchive.validateExemplarIntegrity failed on ${thisUrn}: ${e}")
			}

			return returnVal

	}


}

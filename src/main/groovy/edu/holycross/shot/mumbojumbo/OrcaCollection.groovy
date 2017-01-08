
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
	String exemplarId
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
	OrcaCollection(CiteUrn collectionUrn, String versionString, File orcaFile, String exemplarId, String ctsServiceUrl, String description)
  throws Exception {
    this.collectionUrn = collectionUrn
    this.versionString = versionString
    this.orcaFile = orcaFile
		this.exemplarId = exemplarId
		this.ctsServiceUrl = ctsServiceUrl
		this.description = description


		if ( !(this.orcaFile.exists()) ){
			throw new Exception("OrcaCollection: orcaFile does not exist.")
		}


  }



}

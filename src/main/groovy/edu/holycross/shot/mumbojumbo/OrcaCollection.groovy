
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

  /** Constructor taking String name of a directory.
   * @param collectionUrn a CITE URN
   * @param versionString String defining the version of objects in this collectionUrn
	 * (allows versioned collections to be built independently)
	 * @param orcaFile File, the name of the file that records the ORCA records
	 * @param exemplarId String, the exmplar-id component of an analytical exemplar CTS URN
   * @throws Exception if if orcaFile cannot be found
   */
  OrcaCollection(CiteUrn collectionUrn, String versionString, File orcaFile, String exemplarId, String ctsServiceUrl, String description)
  throws Exception {
    this.collectionUrn = collectionUrn
    this.versionString = versionString
    this.orcaFile = orcaFile
		this.exemplarId = exemplarId
		this.ctsServiceUrl = ctsServiceUrl
		this.description = description

		System.err.println("orcaFile: ${this.orcaFile.getAbsolutePath()}")
		System.err.println("orcaFile exists: ${this.orcaFile.isFile()}")

		if ( !(this.orcaFile.exists()) ){
			throw new Exception("OrcaCollection: orcaFile does not exist.")
		}


  }



}

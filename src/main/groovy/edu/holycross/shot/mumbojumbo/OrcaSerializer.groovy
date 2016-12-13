package edu.holycross.shot.mumbojumbo

import edu.harvard.chs.cite.CiteUrn
import edu.harvard.chs.cite.CtsUrn

/** Class for generating ORCA Collections.
 * The OrcaArchive uses csv or tsv files to create ORCA Collections and corresponding
 * CTS Analytical Exemplar files;
 * ORCA Collections are tsv files; CTS Analytical exemplars are written out as 82xf files.
 */
class OrcaSerializer {


  /** A directory for output */
  File ouputDir


  /** Constructor taking String name of a directory.
   * @param dirName Name of directory with Image Colleciton
   * configuration files.
   * @param invName Name of the inventory file.
   * @throws Exception if a file cannot be made from dirName.
   */
  OrcaSerializer(String outDir)
  throws Exception {
    this.outputDir = new File (outDir)



  }



}

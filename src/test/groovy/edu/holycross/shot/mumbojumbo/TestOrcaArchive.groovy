package edu.holycross.shot.mumbojumbo

import edu.harvard.chs.cite.CtsUrn
import edu.harvard.chs.cite.CiteUrn

import au.com.bytecode.opencsv.CSVReader

import static org.junit.Assert.*
import org.junit.Test

class TestOrcaArchive extends GroovyTestCase {



   @Test void testTesting() {

		 assert true;

     assert shouldFail {
       assert false;
     }
   }


	 @Test void testConstructor(){

    // Path to orca-config
    String orcainventory = "/cite/lib/mumbojumbo/testdata/orcaConf.csv"
    // Path to directory where .tsv or .csv files for ORCA analyses are
    String orcafiles = "/cite/lib/mumbojumbo/testdata/orcaFiles"
    String outputdir = "build/test_output"


    OrcaArchive os = new OrcaArchive(orcainventory, orcafiles, outputdir)

		assert os.outputDir.getAbsolutePath().contains("build/test_output")
		assert os.sourceDir.getAbsolutePath() == orcafiles
		assert os.invFile.getAbsolutePath() == orcainventory

	}
}

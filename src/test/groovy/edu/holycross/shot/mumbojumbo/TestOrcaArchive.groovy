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

		assert os.outputDir.toString() == "build/test_output"
		assert os.sourceDir.toString() == orcafiles
		assert os.invFile.toString() == orcainventory
	}

	@Test void testReadingArchive(){

    // Path to orca-config
    String orcainventory = "/cite/lib/mumbojumbo/testdata/orcaConf.csv"
    // Path to directory where .tsv or .csv files for ORCA analyses are
    String orcafiles = "/cite/lib/mumbojumbo/testdata/orcaFiles"
    String outputdir = "build/test_output"

    OrcaArchive os = new OrcaArchive(orcainventory, orcafiles, outputdir)

		assert os.orcaCollections.size() == 3

	}

	@Test void testCsvFile(){

    // Path to orca-config
    String orcainventory = "/cite/lib/mumbojumbo/testdata/orcaConf.csv"
    // Path to directory where .tsv or .csv files for ORCA analyses are
    String orcafiles = "/cite/lib/mumbojumbo/testdata/orcaFiles"
    String outputdir = "build/test_output"

    OrcaArchive os = new OrcaArchive(orcainventory, orcafiles, outputdir)

		assert os.invFile.getAbsolutePath().contains(".csv")

	}

	@Test void testTsvFile(){

    // Path to orca-config
    String orcainventory = "/cite/lib/mumbojumbo/testdata/orcaConf.tsv"
    // Path to directory where .tsv or .csv files for ORCA analyses are
    String orcafiles = "/cite/lib/mumbojumbo/testdata/orcaFiles"
    String outputdir = "build/test_output"

    OrcaArchive os = new OrcaArchive(orcainventory, orcafiles, outputdir)

		assert os.invFile.getAbsolutePath().contains(".tsv")

	}

	@Test void testNeitherTsvNorCsvFile(){

    // Path to orca-config
    String orcainventory = "/cite/lib/mumbojumbo/testdata/orcaConf.txt"
    // Path to directory where .tsv or .csv files for ORCA analyses are
    String orcafiles = "/cite/lib/mumbojumbo/testdata/orcaFiles"
    String outputdir = "build/test_output"

		assert shouldFail{
	    OrcaArchive os = new OrcaArchive(orcainventory, orcafiles, outputdir)
		}

	}


	@Test void testCollectionsInCsvArchive(){

    // Path to orca-config
    String orcainventory = "/cite/lib/mumbojumbo/testdata/orcaConf.csv"
    // Path to directory where .tsv or .csv files for ORCA analyses are
    String orcafiles = "/cite/lib/mumbojumbo/testdata/orcaFiles"
    String outputdir = "build/test_output"

    OrcaArchive oa = new OrcaArchive(orcainventory, orcafiles, outputdir)

		assert oa.orcaCollections[0].getClass().toString() == "class edu.holycross.shot.mumbojumbo.OrcaCollection"

		oa.orcaCollections.each{ oc ->
				assert oc.getClass().toString() == "class edu.holycross.shot.mumbojumbo.OrcaCollection"
		}

	}

	@Test void testCollectionsInTsvArchive(){

    // Path to orca-config
    String orcainventory = "/cite/lib/mumbojumbo/testdata/orcaConf.tsv"
    // Path to directory where .tsv or .csv files for ORCA analyses are
    String orcafiles = "/cite/lib/mumbojumbo/testdata/orcaFiles"
    String outputdir = "build/test_output"

    OrcaArchive oa = new OrcaArchive(orcainventory, orcafiles, outputdir)

		assert oa.orcaCollections[0].getClass().toString() == "class edu.holycross.shot.mumbojumbo.OrcaCollection"

		oa.orcaCollections.each{ oc ->
				assert oc.getClass().toString() == "class edu.holycross.shot.mumbojumbo.OrcaCollection"
		}

	}

}

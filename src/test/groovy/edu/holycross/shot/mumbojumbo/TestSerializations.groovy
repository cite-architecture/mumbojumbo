package edu.holycross.shot.mumbojumbo

import edu.harvard.chs.cite.CtsUrn
import edu.harvard.chs.cite.CiteUrn

import au.com.bytecode.opencsv.CSVReader

import static org.junit.Assert.*
import org.junit.Test

class TestSerializations extends GroovyTestCase {



   @Test void testTesting() {

		 assert true;

     assert shouldFail {
       assert false;
     }
   }

	@Test void testSerializeToCollection(){

    // Path to orca-config
    String orcainventory = "/cite/lib/mumbojumbo/testdata/orcaConf.tsv"
    // Path to directory where .tsv or .csv files for ORCA analyses are
    String orcafiles = "/cite/lib/mumbojumbo/testdata/orcaFiles"
    String outputdir = "build/test_output"

    OrcaArchive oa = new OrcaArchive(orcainventory, orcafiles, outputdir)

		assert oa.orcaCollections[0].getClass().toString() == "class edu.holycross.shot.mumbojumbo.OrcaCollection"

		oa.orcaCollections.each{ oc ->
				oa.serialize(oc)
		}
	}



}

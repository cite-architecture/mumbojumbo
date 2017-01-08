package edu.holycross.shot.mumbojumbo

import edu.harvard.chs.cite.CtsUrn
import edu.harvard.chs.cite.CiteUrn

import au.com.bytecode.opencsv.CSVReader

import static org.junit.Assert.*
import org.junit.Test

class TestOrcaValidations extends GroovyTestCase {



   @Test void testTesting() {

		 assert true;

     assert shouldFail {
       assert false;
     }
   }



	 @Test void testGoodOrcaLine(){
    // Path to orca-config
    String orcainventory = "/cite/lib/mumbojumbo/testdata/orcaConf.tsv"
    // Path to directory where .tsv or .csv files for ORCA analyses are
    String orcafiles = "/cite/lib/mumbojumbo/testdata/orcaFiles"
    String outputdir = "build/test_output"

    OrcaArchive oa = new OrcaArchive(orcainventory, orcafiles, outputdir)

		 String[] testLine = ["urn:cts:greekLit:tlg0012.tlg001.msA:9.421@Ἀχαιῶν[1]","urn:cite:hmt:place.place96","Ἀχαιῶν"]

		 assert testLine.size() == 3

		 assert oa.validateOrcaLine(testLine)
	 }

	 @Test void testBadOrcaLine1(){
    // Path to orca-config
    String orcainventory = "/cite/lib/mumbojumbo/testdata/orcaConf.tsv"
    // Path to directory where .tsv or .csv files for ORCA analyses are
    String orcafiles = "/cite/lib/mumbojumbo/testdata/orcaFiles"
    String outputdir = "build/test_output"

    OrcaArchive oa = new OrcaArchive(orcainventory, orcafiles, outputdir)

		 String[] testLine = ["urn:cts:greekLit:tlg0012.tlg001.msA:9.421@Ἀχαιῶν[1]","urn:cite:hmt:place.place96"]

		 assert shouldFail{
			 assert oa.validateOrcaLine(testLine)
		 }
	 }


	 @Test void testBadOrcaLine2(){
    // Path to orca-config
    String orcainventory = "/cite/lib/mumbojumbo/testdata/orcaConf.tsv"
    // Path to directory where .tsv or .csv files for ORCA analyses are
    String orcafiles = "/cite/lib/mumbojumbo/testdata/orcaFiles"
    String outputdir = "build/test_output"

    OrcaArchive oa = new OrcaArchive(orcainventory, orcafiles, outputdir)

		 String[] testLine = ["urn:notCts:greekLit:tlg0012.tlg001.msA:9.421@Ἀχαιῶν[1]","urn:cite:hmt:place.place96","Ἀχαιῶν"]

		 assert shouldFail{
			 assert oa.validateOrcaLine(testLine)
		 }
	 }

	 @Test void testBadOrcaLine3(){
    // Path to orca-config
    String orcainventory = "/cite/lib/mumbojumbo/testdata/orcaConf.tsv"
    // Path to directory where .tsv or .csv files for ORCA analyses are
    String orcafiles = "/cite/lib/mumbojumbo/testdata/orcaFiles"
    String outputdir = "build/test_output"

    OrcaArchive oa = new OrcaArchive(orcainventory, orcafiles, outputdir)

		 String[] testLine = ["urn:cts:greekLit:tlg0012.tlg001.msA:9.421@Ἀχαιῶν[1]","urn:not-a-cite-urn","Ἀχαιῶν"]

		 assert shouldFail{
			 assert oa.validateOrcaLine(testLine)
		 }
	 }

	 @Test void testCtsUrns1(){
    // Path to orca-config
    String orcainventory = "/cite/lib/mumbojumbo/testdata/orcaConf.tsv"
    // Path to directory where .tsv or .csv files for ORCA analyses are
    String orcafiles = "/cite/lib/mumbojumbo/testdata/orcaFiles"
    String outputdir = "build/test_output"
		CtsUrn testUrn = new CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:")
		CtsUrn thisUrn

    OrcaArchive oa = new OrcaArchive(orcainventory, orcafiles, outputdir)

		 String[] testLine = ["urn:cts:greekLit:tlg0012.tlg001.msA:9.421@Ἀχαιῶν[1]","urn:cite:hmt:place.place96","Ἀχαιῶν"]

		 thisUrn = new CtsUrn(testLine[0])

		 assert oa.validateExemplarIntegrity(testUrn, testLine)
	 }

	 @Test void testCtsUrns2(){
    // Path to orca-config
    String orcainventory = "/cite/lib/mumbojumbo/testdata/orcaConf.tsv"
    // Path to directory where .tsv or .csv files for ORCA analyses are
    String orcafiles = "/cite/lib/mumbojumbo/testdata/orcaFiles"
    String outputdir = "build/test_output"
		CtsUrn testUrn = new CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:")
		CtsUrn thisUrn

    OrcaArchive oa = new OrcaArchive(orcainventory, orcafiles, outputdir)

		 String[] testLine = ["urn:cts:greekLit:tlg0012.tlg001.XXX:9.421@Ἀχαιῶν[1]","urn:cite:hmt:place.place96","Ἀχαιῶν"]

		 thisUrn = new CtsUrn(testLine[0])

		 assert shouldFail{
			 assert oa.validateExemplarIntegrity(testUrn, testLine)
		 }
	 }


}

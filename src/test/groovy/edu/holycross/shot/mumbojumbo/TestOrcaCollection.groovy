package edu.holycross.shot.mumbojumbo

import edu.harvard.chs.cite.CtsUrn
import edu.harvard.chs.cite.CiteUrn

import au.com.bytecode.opencsv.CSVReader

import static org.junit.Assert.*
import org.junit.Test

class TestOrcaCollection extends GroovyTestCase {



   @Test void testTesting() {

		 assert true;

     assert shouldFail {
       assert false;
     }
   }

	 @Test void testConstructor(){

		 CiteUrn urn = new CiteUrn("urn:cite:hmt:clauseAnalysis")
		 String version = "v1"
		 File of = new File("testdata/orcaConf.tsv")
		 String eid = "clause"
		 String sUrl = "http://localhost:8080/sparqlcts/api?"

		 OrcaCollection oc = new OrcaCollection(urn,version,of,eid,sUrl)

		 assert oc.versionString == "v1"
		 assert oc.orcaFile.isFile()

	 }




}

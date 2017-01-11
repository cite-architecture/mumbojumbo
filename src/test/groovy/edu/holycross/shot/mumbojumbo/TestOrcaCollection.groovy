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
		 File of = new File("testdata/orcaFiles/clauses.csv")
		 CtsUrn aturn = new CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:")
		 String eid = "clause"
		 String sUrl = "http://localhost:8080/sparqlcts/api?"
		 String desc = "Description"

		 OrcaCollection oc = new OrcaCollection(urn,version,aturn,of,eid,sUrl,desc)

		 assert oc.versionString == "v1"
		 assert oc.orcaFile.isFile()

	 }

	 @Test void testBadTextUrn1(){

		 CiteUrn urn = new CiteUrn("urn:cite:hmt:clauseAnalysis")
		 String version = "v1"
		 File of = new File("testdata/orcaFiles/clauses.csv")
		 CtsUrn aturn = new CtsUrn("urn:cts:greekLit:tlg0012:") // text-groujp level, not version level
		 String eid = "clause"
		 String sUrl = "http://localhost:8080/sparqlcts/api?"
		 String desc = "Description"


		 shouldFail{
			 OrcaCollection oc = new OrcaCollection(urn,version,aturn,of,eid,sUrl,desc)
		 }


	 }


	 @Test void testBadTextUrn2(){

		 CiteUrn urn = new CiteUrn("urn:cite:hmt:clauseAnalysis")
		 String version = "v1"
		 File of = new File("testdata/orcaFiles/clauses.csv")
		 CtsUrn aturn = new CtsUrn("urn:cts:greekLit:tlg0012.tlg001:") // work level, not version level
		 String eid = "clause"
		 String sUrl = "http://localhost:8080/sparqlcts/api?"
		 String desc = "Description"

		 shouldFail{
			 OrcaCollection oc = new OrcaCollection(urn,version,aturn,of,eid,sUrl,desc)
		 }

	 }

	 @Test void testBadTextUrn3(){

		 CiteUrn urn = new CiteUrn("urn:cite:hmt:clauseAnalysis")
		 String version = "v1"
		 File of = new File("testdata/orcaFiles/clauses.csv")
		 CtsUrn aturn = new CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA.ex:") // exemplar level, not version level
		 String eid = "clause"
		 String sUrl = "http://localhost:8080/sparqlcts/api?"
		 String desc = "Description"

		 shouldFail{
			 OrcaCollection oc = new OrcaCollection(urn,version,aturn,of,eid,sUrl,desc)
		 }

	 }

}

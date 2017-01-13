
This project has been deprected in favor of a Scala-based approach.

<div style="color: silver">

# "Mumbojumbo":  a build system for OHCO2 Realigned Citable Analyses (ORCAs) #


`mumbojumbo` generates CITE Collections and a CTS Analytical Exemplars from three-column input files of analyses.


## Data sources ##

*Inventory File* a `.tsv` or `.csv` file identifying ORCA collections with the following six columns:

`OrcaCollectionURN \t VersionString \t Filename \t ExemplarID \t CtsServiceUrl \t Description`

*Analysis File* (identified by `Filename`, above) should be documented with three properties:

1. `AnalyzedText` A version-level CTS URN, which may be a range and which may have subreferences.
2. `AnalysisDataUrn` A CITE URN pointing to the analysis, expressed as a CITE Object.
3. `textDeformation` A string that will serve as the 'text content' for a leaf-node in an analytical exemplar.

*Parameters*

1. URL to a CTS Service providing the Version of the text analyzed, *e.g.* `http://localhost:8080/sparqlcts/api?`
2. `orcaCollectionUrn` a collection-level CITE URN. All analyses will be assigned an object-level URN as members of this collection.
3. `exemplarID` a string that will serve as the exemplar-identifier for CTS URNs identifying the generated analytical exemplar.
4. [optional] `collectionVersion` a string that will be used as the version-identifier on the CITE URN for each analysis in the generated collection. If not provided, the default is `v1`.

</div>

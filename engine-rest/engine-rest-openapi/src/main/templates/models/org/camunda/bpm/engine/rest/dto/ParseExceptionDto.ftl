{
  "type" : "object",
  "properties" : {

    <@lib.property
        name = "details"
        type = "object"
        additionalProperties = true
        dto = "ResourceReportDto"
        last = true
        description = "A JSON Object containing list of errors and warnings occurred during deployment." />

  }
}
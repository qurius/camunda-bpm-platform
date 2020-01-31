{
  "operationId" : "queryProcessInstances",
  "description": "Queries for process instances that fulfill given parameters through a JSON object. This method is slightly more powerful than the Get Instances method because it allows filtering by multiple process variables of types String, Number or Boolean.",
  "tags": [
    "Process instance"
  ],
  "parameters" : [
    <#assign last = true >
    <#include "/paths/commons/pagination-params.ftl" >
   ],

  <@lib.requestBody
      dto = "ProcessInstanceQueryDto" />

  "responses" : {

    <@lib.response
        code = "200"
        dto = "ProcessInstanceDto"
        array = true
        desc = "Request successful."/>

    <@lib.response
        code = "400"
        dto = "ExceptionDto"
        last = true
        desc = "Bad Request\n\nReturned if some of the query parameters are invalid, for example if a sortOrder parameter is supplied, but no sortBy, or if an invalid operator for variable comparison is used."/>

  }
}
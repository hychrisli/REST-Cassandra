BASE_URL="http://localhost:8080/cmpe282hongyuanli151/rest"
CURL_GET="curl -sv -X GET ${BASE_URL}"
CURL_POST="curl -sv -X POST ${BASE_URL}"
CURL_PUT="curl -sv -X PUT ${BASE_URL}"
CURL_DELETE="curl -sv -X DELETE ${BASE_URL}"
JSON_PP="json_pp --json_opt=canonical,pretty"


# GET All Employees
${CURL_GET}/employee | ${JSON_PP}

# GET Employee 1
${CURL_GET}/employee/1 | ${JSON_PP}

# POST Create Employee 3
${CURL_POST}/employee -H 'Content-Type: application/json' -d '{"lastname" : "Johnson","firstname" : "Sarah","id" : 3}' | ${JSON_PP}

# PUT Update Employee 2
${CURL_PUT}/employee/2 -H 'Content-Type: application/json' -d '{"firstname" : "Wendy"}' | ${JSON_PP}

# PUT Update Employee 1
${CURL_PUT}/employee/1 -H 'Content-Type: application/json' -d '{"lastname" : "Brown"}' | ${JSON_PP}

# DELETE Employee 3
${CURL_DELETE}/employee/2 | ${JSON_PP}


# All Projects
${CURL_GET}/project ${JSON_PP}
#Project 1
${CURL_GET}/project/1 ${JSON_PP}
#project 3
${CURL_GET}/project/3 ${JSON_PP}

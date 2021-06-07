#!/usr/bin/python3

import http.client
import json
import sys
import os
import urllib.error
import urllib.parse
import urllib.request


def loadLanguage(language, api_token, project_id):
    params = urllib.parse.urlencode(
        {
            'api_token': api_token,
            'action': 'view_terms',
            'id': project_id,
            'type': 'json',
            'language': language
        }
    )
    headers = {
        "Content-type": "application/x-www-form-urlencoded",
        "Accept": "text/plain"
    }
    conn = http.client.HTTPSConnection("poeditor.com")
    conn.request("POST", "/api/", params, headers)
    response = conn.getresponse()
    data = response.read()
    conn.close()

    results = json.loads(data.decode('utf-8')) # obj now contains a dict of the data

    if results['response']['code'] == '200':
        # Open file
        fo = open(localized_dir + "/messages_" + language + ".properties", "wb")

        lineUtf8 = (language + ": \n").encode('UTF-8')
        fo.write(lineUtf8)

        for translation in results['list']:
            if translation['definition']['form'] != "":
                val = translation['definition']['form']
                val = val.replace("\n", "\\n")
                lineUtf8 = ("    " + translation['term'] + ": " + val + "\n").encode('UTF-8')
                fo.write(lineUtf8)

        # Close open file
        fo.close()
    else:
        print("poeditor API responded with error code: " + results['response']['code'])
        sys.exit(1)

localized_dir = os.path.join(sys.path[0], "src/main/resources/i18n")

if not os.path.exists(localized_dir):
    os.makedirs(localized_dir)

# load api token and project id
if len(sys.argv) > 2:
    api_token = sys.argv[1]
    project_id = sys.argv[2]
else:
    import secrets
    api_token = secrets.api_token
    project_id = secrets.project_id

print('project_id: ' + project_id)
print('api_token: ' + api_token)

loadLanguage("en", api_token, project_id)  # english
loadLanguage("fr", api_token, project_id)  # french
loadLanguage("de", api_token, project_id)  # german
loadLanguage("it", api_token, project_id)  # italian
loadLanguage("rm", api_token, project_id)  # rumantsch

print('Successfully loaded terms')
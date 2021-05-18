# Getting the translations

This project contains localized strings for which are kept in the `i18n` subdirectory. These need to be updated manually.

If you have your own translations on POEditor, you can simply run `getTranslations.py` with Python 3. You will need to create a `secrets.py` file in the same directory as the python script with the following content:
```
api_token = 'YOUR_POEDITOR_API_KEY'
project_id = 'YOUR_POEDITOR_PROJECT_ID'
```

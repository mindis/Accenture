# tutorial/01-create-workspace.py
import os
from azureml.core import Workspace
from azureml.core.authentication import ServicePrincipalAuthentication

#ws = Workspace.create(name='accml', # provide a name for your workspace
#                      subscription_id='c46a9435-c957-4e6c-a0f4-b9a597984773', # provide your subscription ID
#                      resource_group='accenture', # provide a resource group name
#                      create_resource_group=True,
#                      location='Central USpyt') # For example: 'westeurope' or 'eastus2' or 'westus2' or 'southeastasia'.



subscription_id = 'c46a9435-c957-4e6c-a0f4-b9a597984773'
resource_group = 'accenture'
workspace_name = 'accml'
location = 'eastus2'

#svc_pr_password = os.environ.get("AZUREML_PASSWORD")
svc_pr_password = "1fY58u0dpP1Yg-i.A~rUp_iz04RxWUFSwv"

svc_pr = ServicePrincipalAuthentication(
    tenant_id="72f988bf-86f1-41af-91ab-2d7cd011db47",
    service_principal_id="8a3ddafe-6dd6-48af-867e-d745232a1833",
    service_principal_password=svc_pr_password)


ws = Workspace(
    subscription_id=subscription_id,
    resource_group=resource_group,
    workspace_name=workspace_name,
    #location=location,
    auth=svc_pr
    )

print("Found workspace {} at location {}".format(ws.name, ws.location))

import azureml.core
print(azureml.core.VERSION)

# write out the workspace details to a configuration file: .azureml/config.json
#ws.write_config(path='.azureml')


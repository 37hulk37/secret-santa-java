import requests

r = requests.post('http://127.0.0.1/register-user', json={
    "name": "Jason Burne"})
print(f"Status Code: {r.status_code}, Response: {r}, {r.content}")

r = requests.get('http://127.0.0.1/get-users')
print(f"Status Code: {r.status_code}, Response: {r}, {r.content}")

r = requests.post('http://127.0.0.1/create-group', json={
   "userId": "1",
        "groupName": "agents"})
print(f"Status Code: {r.status_code}, Response: {r}, {r.content}")

r = requests.get('http://127.0.0.1/get-groups')
print(f"Status Code: {r.status_code}, Response: {r}, {r.content}")

r = requests.post('http://127.0.0.1/register-user', json={
    "name": "Roger Waters"})
print(f"Status Code: {r.status_code}, Response: {r}, {r.content}")

r = requests.post('http://127.0.0.1/add-to-group', json={
    "userId": "2",
        "groupName": "agents"})
print(f"Status Code: {r.status_code}, Response: {r}, {r.content}")

##r = requests.post('http://127.0.0.1/leave-group', json={
##   "userId": "2",
##           "groupName": "agents"})
##print(f"Status Code: {r.status_code}, Response: {r}, {r.content}")

r = requests.get('http://127.0.0.1/get-groups')
print(f"Status Code: {r.status_code}, Response: {r}, {r.content}")

r = requests.post('http://127.0.0.1/start-secret-santa', json={
   "userId": "1",
           "groupName": "agents"})
print(f"Status Code: {r.status_code}, Response: {r}, {r.content}")

r = requests.get('http://127.0.0.1/get-recipient', json={
    "userId":"1"})
print(f"Status Code: {r.status_code}, Response: {r}, {r.content}")

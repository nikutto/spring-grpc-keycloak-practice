## Example for getting an access token by keycloak

curl -d "grant_type=password&username=hoge&password=pass&client_id=sprin
g-boot-app&client_secret=3cIAhceoDDL7agn72DrKeimccTXFLE7f" localhost:8080/realms/base-realm/protocol/openid-connect/t
oken

## Example for testing grpc authed test

grpcurl -plaintext -H "Authorization: Bearer $ACCESS_TOKEN" localhost:6565 helloworld.Greeter.SayHello


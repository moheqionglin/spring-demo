# Create us a key. Don't bother putting a password on it since you will need it to start apache. If you have a better work around I'd love to hear it.
openssl genrsa -out server/server.key
# Take our key and create a Certificate Signing Request for it.
openssl req -new -key server/server.key -out server/server.csr
# Sign this bastard key with our bastard CA key.
openssl ca -in server/server.csr -cert private/ca.crt -keyfile private/ca.key -out server/server.crt -config "/Users/shixin.zhang/ca/conf/openssl.conf"


#!/bin/sh
# The base of where our SSL stuff lives.
base="/Users/shixin.zhang/ca"
# Were we would like to store keys... in this case we take the username given to us and store everything there.
mkdir -p $base/users/
 
# Let's create us a key for this user... yeah not sure why people want to use DES3 but at least let's make us a nice big key.
openssl genrsa -des3 -out $base/users/client.key 1024
# Create a Certificate Signing Request for said key.
openssl req -new -key $base/users/client.key -out $base/users/client.csr
# Sign the key with our CA's key and cert and create the user's certificate out of it.
openssl ca -in $base/users/client.csr -cert $base/private/ca.crt -keyfile $base/private/ca.key -out $base/users/client.crt -config "/Users/shixin.zhang/ca/conf/openssl.conf"
 
# This is the tricky bit... convert the certificate into a form that most browsers will understand PKCS12 to be specific.
# The export password is the password used for the browser to extract the bits it needs and insert the key into the user's keychain.
# Take the same precaution with the export password that would take with any other password based authentication scheme.
openssl pkcs12 -export -clcerts -in $base/users/client.crt -inkey $base/users/client.key -out $base/users/client.p12

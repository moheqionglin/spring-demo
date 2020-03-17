i#!/bin/sh
# Generate the key.
openssl genrsa -out private/ca.key
# Generate a certificate request.
openssl req -new -key private/ca.key -out private/ca.csr
# Self signing key is bad... this could work with a third party signed key... registeryfly has them on for $16 but I'm too cheap lazy to get one on a lark.
# I'm also not 100% sure if any old certificate will work or if you have to buy a special one that you can sign with. I could investigate further but since this
# service will never see the light of an unencrypted Internet see the cheap and lazy remark.
# So self sign our root key.
openssl x509 -req -days 365 -in private/ca.csr -signkey private/ca.key -out private/ca.crt
# Setup the first serial number for our keys... can be any 4 digit hex string... not sure if there are broader bounds but everything I've seen uses 4 digits.
echo FACE > serial
# Create the CA's key database.
touch index.txt
# Create a Certificate Revocation list for removing 'user certificates.'
openssl ca -gencrl -out /Users/shixin.zhang/ca/private/ca.crl -crldays 7 -config "/Users/shixin.zhang/ca/conf/openssl.conf"


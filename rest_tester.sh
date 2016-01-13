#!/bin/sh

COOKIE_FILE=cookie.txt

# get the csrf token
CSRF_TOKEN=`curl -s -c $COOKIE_FILE http://localhost:8080/pa165/login \
    | sed -n 's/.*name="_csrf" value="\(.*\)".*/\1/p'` 

# login as admin
curl -c $COOKIE_FILE -b $COOKIE_FILE --header "X-CSRF-TOKEN: $CSRF_TOKEN" \
    -d username=admin -d password=admin \
    http://localhost:8080/pa165/login 

# renew the csrf token
CSRF_TOKEN=`curl -s -b $COOKIE_FILE http://localhost:8080/pa165/login \
    | sed -n 's/.*name="_csrf" value="\(.*\)".*/\1/p'` 

# do the given command
curl -b $COOKIE_FILE --header "X-CSRF-TOKEN: $CSRF_TOKEN" -i "$@" 

if [ -e $COOKIE_FILE ]
then
    rm $COOKIE_FILE
fi

# just print a new line
echo

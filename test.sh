BASEURL=http://127.0.0.1:8080

function request(){
    verbe=$1
    ressource=$2
    echo $verbe $ressource
    if [ -n "$3" ]; then
        # extra data as third argument !
        curl -X $verbe --data-binary "$3" $BASEURL$ressource
    else
        curl -X $verbe $BASEURL$ressource
    fi
}

git clone http://github.com/calve/prof tmp
cd tmp
python -m pyftpdlib -w &
sleep 3
request "GET" "/"
request "GET" "/run.py"
request "GET" "/src/"
request "DELETE" "/run.py"

echo "Should return 404"
request "GET" "/run.py"

request "POST" "/post_form_brigde.py" "hello, brigde"
request "GET" "/post_form_bridge.py"
request "DELETE" "/post_form_bridge.py"

cd ..
rm -Rvf tmp > /dev/null
kill %1
echo "pyftpdlib killed"

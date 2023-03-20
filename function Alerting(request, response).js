function Alerting(request, response)

{

print("Executing function alerting");

//Do something

response.code = 200;

response.send();

function callback(userdata) {
    Shelly.call("Switch.Toggle","{ id:0 }",null,null);
  }
  
  let timer_handle = Timer.set(500,true,callback,null); 

}

HTTPServer.registerEndpoint("alerting", Alerting);


function Held(request, response)

{

print("Executing function alerting");

//Do something

response.code = 200;

response.send();

function callback(userdata) {
    Shelly.call("Switch.Toggle","{ id:0 }",null,null);
  }
  
  let timer_handle = Timer.set(1500,true,callback,null); 

}

HTTPServer.registerEndpoint("held", Held);
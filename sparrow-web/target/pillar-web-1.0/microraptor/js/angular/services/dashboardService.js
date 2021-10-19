define([],function(){

return ['$resource',function  ($resource) {

	// body...http://heaerieglobalsolutions.com:8080/pillar/api/v2/mail/
	return $resource('/pillar/api/v3/mail/', null,
    {
        mail  : { method: 'GET', isArray: true}  
    });
}
];

});
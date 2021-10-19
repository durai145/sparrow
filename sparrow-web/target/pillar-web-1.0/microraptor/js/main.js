/**
 * configure RequireJS
 * prefer named modules to long paths, especially for version mgt
 * or 3rd party libraries
 */
require.config({

    /* baseUrl: "js/lib", */
     paths: {
           'angular'           : './external/angular/angular'
           ,'ufi'              : './external/angular/ufi'
          , 'angularUiRouter'           : './external/angular/angular-ui-router'
          , 'angularAnimate'           : './external/angular/angular-animate'
         , 'angularRoute'      : './external/angular-route/angular-route'
             , 'ngResource'        : './external/angular-route/angular-route'
             , 'bootstrap'         : './external/bootstrap/dist/js/bootstrap'
             , 'domReady'          : './external/requirejs-domready/domReady'
         , 'controllers'       : './angular/controllers/'
         , 'services'          : './angular/services/'
         , 'directives'        : './angular/directives/'
         , 'filters'           : './angular/filters/'
             , 'jquery'            : './jquery'
         , 'app'               : './angular/app'
         , 'ngResource'        : './external/angular-resource/angular-resource'
         , 'angularSanitize'   : './external/angular-resource/angular-sanitize'
         , 'jqueryFlot'        : './external/jquery/jquery-flot'
         , 'excanvas'          : './external/jquery/excanvas'
         , 'jqueryFlotPie'     : './external/jquery/jquery-flot-pie'
         , 'toaster'           :'./external/angularjs-toaster/toaster'
         , 'ufi.xml'           :'./ufi.xml'
         , 'ufi.core'           :'./ufi.core'
         , 'ufi.frameGen'           :'./ufi.frameGen'
         , 'ufi.validate'           :'./ufi.validate'
         , 'require'           :'./require'
         , 'backbone'           :'./backbone'
         , 'underscore'           :'./underscore'
         /* , 'toaster'       : './external/angular/toaster'*/
         /*, 'toasterService': './angular/services/toasterService'*/
         },
 
     /**
      * for libs that either do not support AMD out of the box, or
      * require some fine tuning to dependency mgt'
      */
     shim: {
 
           'bootstrap'     :['jquery'        ]
         , 'angular'       : { 'exports':'angular'}
        // , 'angularRoute'  : { exports:'angularRoute'}
         , 'app'           :['angular'        ]
         , 'app'           :['jquery'         ]
         , 'jqueryFlot'    :['jquery'         ]
         , 'excanvas'      :['jquery'         ]
         , 'jqueryFlotPie' :['jqueryFlot'     ]
         , 'app'           :['angularRoute'   ]
         , 'app'           :['services'       ]
         , 'app'           :['require'        ]
         , 'angularRoute'  :{ 'deps' :['angular' , 'ngResource'] }
         , 'ngResource'    :['angular'        ]
         , 'toaster'       :['angular'        ]
         , 'ufi'           :['angular'       ]
         , 'toasterService':['toaster'        ]
         , 'services'      :['toasterService' ]
         , 'controllers'   :['angular'        ]
         , 'filters'       :['angular'        ]
         , 'toaster'       :['angular'        ]
         , 'directives'    :['angular'        ]
         , 'resource'      :['angular'        ]
         , 'jqueryFlot'    :['jquery'         ]
         , 'angularUiRouter' : ['angular']
         , 'angularAnimate'  : ['angular']
         , "ufi.core"        : ["require"]
         , "backbone"        : ["underscore"]
         , "ufi.core"        : ["backbone"]
         , "ufi.frameGen"    : ["ufi.core"]
         , "ufi.xml"         : ["ufi.core"]
         , "ufi.validate"    : ["ufi.core"]
         , "ufi.frameGen"    : { exports:'ufiFrameGen'}
         , 'app'            :{ 'deps':['angular','jquery','angularRoute','ngResource','angularUiRouter','angularAnimate','excanvas','jqueryFlot','jqueryFlotPie','toaster','ufi','ufi.frameGen','ufi.validate', 'ufi.xml']}
         
     }
     /*
     deps: [
         // kick start application... see bootstrap.js
         './bootstrap'
     ]
     */
 });
 
 requirejs([
     'require' ,
     'angular' ,
     'app'     ,
     'jquery'  ,
     'ngResource'
 ], function (require, angular,app, $, ngResource) {
     'use strict';
 
     /*
      * place operations that need to initialize prior to app start here
      * using the `run` function on the top-level module
 
     
      */
   //  console.log('ufi');
    //  console.log(ufi);
 
      ///exports.ufiFrameGen = new ufiFrameGen();
      console.log(angular);
     // console.log(angularRoute);
      console.log(ngResource);
 
     //require(['domReady!'], function (document) {
        angular.element(document).ready(function() {
       angular.bootstrap(document, ['app']);
     });
     //});
 
 });
 
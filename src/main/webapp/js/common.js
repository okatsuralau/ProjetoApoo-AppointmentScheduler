//The build will inline common dependencies into this file.

requirejs.config({
  baseUrl: ROOT + 'js/',
  paths: {
	'jquery'     :                'vendor/jquery',
	'bootstrap'  :                'vendor/bootstrap.min',
	'classie'    :                'vendor/classie',
	'mlpushmenu' :                'vendor/mlpushmenu'
  },
  shim: {
	'bootstrap'  :                ['jquery'],
	'mlpushmenu' : {
		deps    : ['classie'],
		exports : 'mlPushMenu'
	}
  }
});

// Load the main app module to start the app
requirejs(["app/main"]);

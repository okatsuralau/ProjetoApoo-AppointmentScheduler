/*!
* Admin
* admin/main.js
* Funções comuns a todos os arquivos do site admin
*/

define(
  [
	'jquery',
	'bootstrap',
	'mlpushmenu'
  ],
  function ($, bootstrap, mlPushMenu) {
	'use strict';

	$(function() {

		$( 'a[href="#"]' ).click( function(e) {
			e.preventDefault();
		});

		if($.fn.niceScroll) {
			$('.scroller-inner').getNiceScroll().resize();
		}

		/* Tooltips */
		if( $.fn.tooltip ){
			$('[data-toggle^="tooltip"]').tooltip();

			$('.tooltip-alert').tooltip();
		}


		/**
		 * Ativa o push menu
		 */
		new mlPushMenu( document.getElementById( 'mp-menu' ), document.getElementById( 'trigger' ), {
			type : 'cover'
		} );
	});
});

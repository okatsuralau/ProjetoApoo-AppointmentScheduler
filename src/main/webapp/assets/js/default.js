/*!
* Admin
* admin/default.js
* Funções comuns a todos os arquivos do site
*/
/**
 * Document ready
 *
 * @return void
 */
;$(function() {

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

// Transform alert messages in pnotify alerts
// consume_alert();

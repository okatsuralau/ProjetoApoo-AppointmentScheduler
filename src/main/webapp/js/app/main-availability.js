//
// Availability Model
//
define(['jquery'], function ($) {
	$(function() {
		var $panel      = $('#PanelList');
		var $panel_body = $panel.find('.panel-body');
		var $tbody      = $('#tabela').find('tbody');

		$('.navbar-form').find('select').on('change', function(){
			var $jumbotron = $('<div class="jumbotron lead text-center no-mb"></div>');

			var model_id = $(this).val();

			if(model_id != "" || model_id != 0){
				$.ajax({
					type     : "GET",
					url      : $(this).parents('form').attr('action') + '/' + model_id,
					cache    : false,
					dataType : "json"
				}).done(function(response){
					console.log(response);

					$tbody.html(""); // Limpa os resultados anteriores
					$jumbotron.html('Carregando...');
					$jumbotron.appendTo("#PanelList").find('.panel-body');
					$panel_body.html($jumbotron);

					if(response.size > 0){
						var $tr = $('<tr>');

						$.each(response.data, function(index, val) {
							$tr
							.clone()
							.append('<td>' + val.doctor.individual.first_name + '</td>')
							.append('<td>' + val.expertise.title + '</td>')
							.append('<td>' + val.office.title + '</td>')
							.append('<td>' + val.availability_date + '</td>')
							.append('<td>' + val.availability_time + '</td>')
							.append('<td>' + val.available_amount + '</td>')
							.append('<td>' + val.scheduled_amount + '</td>')
							.append('<td>' + (val.available_amount - val.scheduled_amount) + '</td>')
							.appendTo($tbody);
						});
						$jumbotron.remove();
						$panel_body.html("");
					}else{
						$jumbotron.html('<i class="glyphicon glyphicon-thumbs-down size-x3"></i><br>' + "Nenhum elemento encontrado").appendTo($panel_body);
					}
				}).fail(function(jqXHR, textStatus) {
					$jumbotron.html("<i class=\"glyphicon glyphicon-thumbs-down size-x3\"></i><br>" + "Não foi possível capturar as informações").appendTo($panel_body);
				});//end ajax
			}
		});
	});
});

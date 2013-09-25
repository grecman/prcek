$(document).ready(
		function() {

			$.datepicker.regional['cs'] = {
				closeText : 'Cerrar',
				prevText : 'Pøedchozí',
				nextText : 'Další',
				currentText : 'Hoy',
				monthNames : [ 'Leden', 'Únor', 'Bøezen', 'Duben', 'Kvìten',
						'Èerven', 'Èervenec', 'Srpen', 'Záøí', 'Øíjen',
						'Listopad', 'Prosinec' ],
				monthNamesShort : [ 'Le', 'Ún', 'Bø', 'Du', 'Kv', 'Èn', 'Èc',
						'Sr', 'Zá', 'Øí', 'Li', 'Pr' ],
				dayNames : [ 'Nedìle', 'Pondìlí', 'Úterý', 'Støeda', 'Ètvrtek',
						'Pátek', 'Sobota' ],
				dayNamesShort : [ 'Ne', 'Po', 'Út', 'St', 'Èt', 'Pá', 'So', ],
				dayNamesMin : [ 'Ne', 'Po', 'Út', 'St', 'Èt', 'Pá', 'So' ],
				weekHeader : 'Sm',
				dateFormat : 'dd.mm.yy',
				firstDay : 1,
				isRTL : false,
				showMonthAfterYear : false,
				yearSuffix : ''
			};

			$.datepicker.setDefaults($.datepicker.regional['cs']);

			$("#datepicker").datepicker();
		});
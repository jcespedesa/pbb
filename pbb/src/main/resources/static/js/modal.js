$(document).ready(function()
{

	$('.table.deleteBtn').on('click',function(event)
	{
		event.preventDefault();
				
		var href=$(this).attr('href');

		$('#confirmDelete #delRef').attr('href',href);
		$('#confirmDelete').modal();

	});


});	
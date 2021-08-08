
$(document).ready(function()
{

	
	function load_json_data(city,state) 
	{
		var html_code='';
		
			  
		$.getJSON('/json/citiesListJson.json',function(data)
		{
			
			html_code += '<option value="">Select '+ city +'</option>';
	   
			$.each(data,function(key,value)
			{
							
				if(state == value.state)
				{
					
					html_code += '<option value="'+ value.city +'">'+ value.city +'</option>';
					
				}
								
			});
			
			$('#'+ city).html(html_code);
		});	
	 }

	
	$(document).on('change','#state',function()
	{
		var state=$(this).val();
		
		  
		if(state != '')
		{
			
			load_json_data('city',state);
		}
		else
		{
			
			$('#city').html('<option value="">Select city</option>');
		}
	});
});


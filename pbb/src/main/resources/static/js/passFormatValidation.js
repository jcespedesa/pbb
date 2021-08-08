
	$(document).ready(function() 
	{
		$('#passForm').submit(function(e)
		{
		      var form=this;
		      e.preventDefault();
		      // Check Passwords are the same
		      if($('#psw1').val()==$('#psw2').val()) 
		      {
		          // Sending Form
		          form.submit();
		      }
		      else 
		      {
		          // Complain bitterly
		          alert('Password Mismatch, please retype password...');
		          
		          $('#psw1').val('');
		          $('#psw2').val('');
		          
		          return false;
		      }
		  });
		});
	
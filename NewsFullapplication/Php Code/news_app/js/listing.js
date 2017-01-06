(function($,W,D)
{
    var JQUERY4U = {};

    JQUERY4U.UTIL =
    {
        setupFormValidation: function()
        {
            //form validation rules
            $("#addlisting").validate({
                rules: {
                    category_id: "required",
										sub_cat_id: "required",
										company_name: "required",
										company_address: "required",										 
										contact_no:{ 
											required: true,
											digits:true	
										},										 
										company_email: {
                        required: true,
                        email: true
                    }                     
									},
                   
                messages: {
                    category_id: "Please select category",
										sub_cat_id: "Please select sub category",
										company_name: "Please enter company name",
										company_address: "Please enter address",
										contact_no: "Please enter contact number",
										company_email: "Please enter a valid email address"
                     
                },
                submitHandler: function(form) {
                    form.submit();
                }
            });
        }
    }

    //when the dom has loaded setup form validation rules
    $(D).ready(function($) {
        JQUERY4U.UTIL.setupFormValidation();
    });

})(jQuery, window, document);
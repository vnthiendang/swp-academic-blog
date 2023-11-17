// variable_names
// functionNames
// CONSTANT_VARIABLE_NAMES
// $_my_jquery_selected_element

if(typeof String.prototype.trim !== 'function') {
    
    String.prototype.trim = function()
    {
        return this.replace(/^\s+|\s+$/g, '');
    }
}

var checkbox_select = function(params)
{
    // Error handling first
    // ----------------------------------------------------------------------------------------------------
    
    var error = false;

    // If the selector is not given
    if(!params.selector) {                                              console.error("selector needs to be defined"); error = true; }

    // If the selector is not a string
    if(typeof params.selector != "string") {                            console.error("selector needs to be a string"); error = true; }

    // If the element is not a form
    if(!$(params.selector).is("form")) {                                console.error("Element needs to be a form"); error = true; }

    // If the element doesn't contain a select
    if($(params.selector).find("select").length < 1) {                  console.error("Element needs to have a select in it"); error = true; }

    // If the element doesn't contain option elements
    if($(params.selector).find("option").length < 1) {                  console.error("Select element needs to have an option in it"); error = true; }

    // If the select element doesn't have a name attribute
    if($(params.selector).find('select').attr('name') == undefined) {   console.error("Select element needs to have a name attribute"); error = true; }

    // If there was an error at all, dont continue in the code.
    if(error)
        return false;

    // ----------------------------------------------------------------------------------------------------

    var that            = this,
        $_native_form   = $(params.selector),
        $_native_select = $_native_form.find('select'),
        
        // Variables
        selector                = params.selector,
        select_name             = $_native_select.attr('name').charAt(0).toUpperCase() + $_native_select.attr('name').substr(1),
        selected_translation    = params.selected_translation   ? params.selected_translation   : "selected",
        all_translation         = params.all_translation        ? params.all_translation        : "All " + select_name + "s",
        not_found_text          = params.not_found              ? params.not_found              : "No " + select_name + "s found.",
        currently_selected      = [],
        
        // Create the elements needed for the checkbox select
        $_parent_div    = $("<div />")      .addClass("checkbox_select"),
        $_select_anchor = $("<a />")        .addClass("checkbox_select_anchor")     .text( select_name ),
        $_search        = $("<input />")    .addClass("checkbox_select_search"),
        $_submit        = $("<input />")    .addClass("checkbox_select_submit")     .val("Apply") .attr('type','submit') .data("selected", ""),
        $_dropdown_div  = $("<div />")      .addClass("checkbox_select_dropdown"),
        $_not_found     = $("<span />")     .addClass("not_found hide")             .text(not_found_text),
        $_ul            = $("<ul />"),

        updateCurrentlySelected = function()
        {
            var selected = [];

            $_ul.find("input:checked").each(
                                                        
                function()
                {
                    selected.push($(this).val());
                }
            );

            currently_selected = selected;

            if(selected.length == 0)
            {
                    $_select_anchor.text( select_name )
            }
            else if(selected.length == 1)
            {
                $_select_anchor.text( selected[0] + " " + selected_translation );
            }
            else if(selected.length ==  $_ul.find("input[type=checkbox]").length)
            {
                $_select_anchor.text( all_translation );
            }
            else
            {
                $_select_anchor.text( selected.length + " " + selected_translation );
            }
        },

        // Template for the li, will be used in a loop.
        createItem  = function(name, value, count)
        {
            var uID             = 'checkbox_select_' + select_name + "_" + name.replace(" ", "_"),
                $_li            = $("<li />"),
                $_checkbox      = $("<input />").attr(
                                        {
                                            'name'  : name,
                                            'id'    : uID,
                                            'type'  : "checkbox",
                                            'value' : value
                                        }
                                    )
                                    .click(

                                        function()
                                        {
                                            updateCurrentlySelected();
                                        }
                                    ),

                $_label         = $("<label />").attr('for', uID),
                $_name_span     = $("<span />").text(name).prependTo($_label),
                $_count_span    = $("<span />").text(count).appendTo($_label);
                        
            return $_li.append( $_checkbox.after( $_label ) );
        },
		
		apply = function()
		{
			$_dropdown_div.toggleClass("show");
			$_parent_div.toggleClass("expanded");
				
			if(!$_parent_div.hasClass("expanded"))
			{  
				// Only do the Apply event if its different
				if(currently_selected != $_submit.data("selected"))
				{
					$_submit.data("selected" , currently_selected);

					that.onApply(
						{ 
							selected : $_submit.data("selected")
						}
					);
				}		
			}					
		};
    
    // Event of this instance
    that.onApply = typeof params.onApply == "function" ? 
                
                    params.onApply :
                
                    function(e) 
                    {
                        //e.selected is accessible
                    };

    that.update = function() 
    {
        $_ul.empty();
        $_native_select.find("option").each(

            function(i)
            {
                $_ul.append( createItem( $(this).text(), $(this).val(), $(this).data("count") ) );
            }
        );

        updateCurrentlySelected();
    }

    that.check = function(checkbox_name, checked) 
    {
        //$_ul.find("input[type=checkbox][name=" + trim(checkbox_name) + "]").attr('checked', checked ? checked : false);

		$_ul.find("input[type=checkbox]").each(function()
		{
			// If this elements name is equal to the one sent in the function
			if($(this).attr('name') == checkbox_name)
			{
				// Apply the checked state to this checkbox
				$(this).attr('checked', checked ? checked : false);
				
				// Break out of each loop
				return false;
			}
		});
		
        updateCurrentlySelected();

    }

    // Build mark up before pushing into page
    $_dropdown_div  .prepend($_search);
    $_dropdown_div  .append($_ul);
    $_dropdown_div  .append($_not_found);
    $_dropdown_div  .append($_submit);
    $_dropdown_div  .appendTo($_parent_div);
    $_select_anchor .prependTo($_parent_div);

    // Iterate through option elements
    that.update();

    // Events 

    // Actual dropdown action
    $_select_anchor.click( 

        function()
        {
            apply();
        }
    );
             
    // Filters the checkboxes by search on keyup
    $_search.keyup(

        function()
        {
            var search = $(this).val().toLowerCase().trim();

            if( search.length == 1 )
            {
                $_ul.find("label").each(

                    function()
                    {
                        if($(this).text().toLowerCase().charAt(0) == search.charAt(0))
                        {
                            if($(this).parent().hasClass("hide"))
                                $(this).parent().removeClass("hide");

                            if(!$_not_found.hasClass("hide"))
                                $_not_found.addClass("hide");
                        }
                        else
                        {
                            if(!$(this).parent().hasClass("hide"))
                                $(this).parent().addClass("hide");

                            if($_not_found.hasClass("hide"))
                                $_not_found.removeClass("hide");
                        }
                    }
                );
            }
            else
            {
                // If it doesn't contain 
                if($_ul.text().toLowerCase().indexOf(search) == -1)
                {
                    if($_not_found.hasClass("hide"))
                        $_not_found.removeClass("hide");
                }
                else
                {
                    if(!$_not_found.hasClass("hide"))
                        $_not_found.addClass("hide");
                }
                    
                $_ul.find("label").each(

                    function()
                    {
                        if($(this).text().toLowerCase().indexOf(search) > -1)
                        {
                            if($(this).parent().hasClass("hide"))
                                $(this).parent().removeClass("hide");
                        }
                        else
                        {
                            if(!$(this).parent().hasClass("hide"))
                                $(this).parent().addClass("hide");
                        }
                    }
                );
            }
        }
    );

    $_submit.click(
                
        function(e)
        {
            e.preventDefault();

            apply();
        }
    );

    // Delete the original form submit
    $(params.selector).find('input[type=submit]').remove();

    // Put finalized markup into page.
    $_native_select.after($_parent_div);

    // Hide the original element
    $_native_select.hide();
};
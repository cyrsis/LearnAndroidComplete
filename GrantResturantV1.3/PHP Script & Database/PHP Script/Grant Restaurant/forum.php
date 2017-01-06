<!DOCTYPE html>

<html lang="en">

<head>

    <meta charset="utf-8">

    <title>Forms | Slate Admin 2.0</title>



    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">

    <meta name="apple-mobile-web-app-capable" content="yes">





    <link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/bootstrap-responsive.css" rel="stylesheet">
    <link href="css/bootstrap-overrides.css" rel="stylesheet">
    <link href="css/ui-lightness/jquery-ui-1.8.21.custom.css" rel="stylesheet">
    <link href="css/slate.css" rel="stylesheet">
    <link href="css/slate-responsive.css" rel="stylesheet">

    <script src="js/jquery-1.7.2.min.js"></script>
    <script src="js/jquery-ui-1.8.21.custom.min.js"></script>
    <script src="js/jquery.ui.touch-punch.min.js"></script>
    <script src="js/bootstrap.js"></script>
    <script src="js/jquery.validate.js"></script>
    <script src="js/demo.validate.js"></script>





    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->

    <!--[if lt IE 9]>

    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>

    <![endif]-->



</head>



<body>





<div id="header">



    <div class="container">



        <h1><a href="">Slate Admin 2.0</a></h1>



        <div id="info">



            <a href="javascript:;" id="info-trigger">

                <i class="icon-cog"></i>

            </a>



            <div id="info-menu">



                <div class="info-details">



                    <h4>Welcome back, John D.</h4>



                    <p>

                        Logged in as Admin.

                        <br>

                        You have <a href="javascript:;">5 messages.</a>

                    </p>



                </div> <!-- /.info-details -->



                <div class="info-avatar">



                    <img src="img/avatar.jpg" alt="avatar">



                </div> <!-- /.info-avatar -->



            </div> <!-- /#info-menu -->



        </div> <!-- /#info -->



    </div> <!-- /.container -->



</div> <!-- /#header -->





<div id="nav">



    <div class="container">



        <a href="javascript:;" class="btn-navbar" data-toggle="collapse" data-target=".nav-collapse">

            <i class="icon-reorder"></i>

        </a>



        <div class="nav-collapse">



            <ul class="nav">



                <li class="nav-icon">

                    <a href="">

                        <i class="icon-home"></i>

                        <span>Home</span>

                    </a>

                </li>



                <li class="dropdown active">

                    <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown">

                        <i class="icon-th"></i>

                        Components

                        <b class="caret"></b>

                    </a>



                    <ul class="dropdown-menu">

                        <li><a href="forms.html">Forms</a></li>

                        <li><a href="ui-elements.html">UI Elements</a></li>

                        <li><a href="grid.html">Grid Layout</a></li>

                        <li><a href="tables.html">Tables</a></li>

                        <li><a href="widgets.html">Widget Boxes</a></li>

                        <li><a href="charts.html">Charts</a></li>

                        <li><a href="tabs.html">Tabs & Accordion</a></li>

                        <li><a href="buttons.html">Buttons</a></li>

                    </ul>

                </li>



                <li class="dropdown">

                    <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown">

                        <i class="icon-copy"></i>

                        Sample Pages

                        <b class="caret"></b>

                    </a>



                    <ul class="dropdown-menu">

                        <li><a href="invoice.html">Invoice</a></li>

                        <li><a href="faq.html">FAQ</a></li>

                        <li><a href="pricing.html">Pricing Plans</a></li>

                        <li><a href="gallery.html">Image Gallery</a></li>

                        <li><a href="wizard.html">Wizard</a></li>

                        <li><a href="reports.html">Reports</a></li>

                        <li><a href="calendar.html">Calendar</a></li>

                    </ul>

                </li>



                <li class="dropdown">

                    <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown">

                        <i class="icon-external-link"></i>

                        Other Pages

                        <b class="caret"></b>

                    </a>



                    <ul class="dropdown-menu">

                        <li><a href="login.html">Login</a></li>

                        <li><a href="signup.html">Signup</a></li>

                        <li><a href="error.html">Error</a></li>

                        <li class="dropdown">

                            <a href="javascript:;">

                                Dropdown Menu

                                <i class="icon-chevron-right sub-menu-caret"></i>

                            </a>



                            <ul class="dropdown-menu sub-menu">

                                <li><a href="javascript:;">Dropdown #1</a></li>

                                <li><a href="javascript:;">Dropdown #2</a></li>

                                <li><a href="javascript:;">Dropdown #3</a></li>

                                <li><a href="javascript:;">Dropdown #4</a></li>

                            </ul>

                        </li>

                    </ul>

                </li>

            </ul>

            <ul class="nav pull-right">

                <li class="">

                    <form class="navbar-search pull-left">

                        <input type="text" class="search-query" placeholder="Search">

                        <button class="search-btn"><i class="icon-search"></i></button>

                    </form>
                </li>
            </ul>
        </div> <!-- /.nav-collapse -->
    </div> <!-- /.container -->
</div> <!-- /#nav -->

<div id="content">

    <div class="container">

        <div id="page-title" class="clearfix">

            <ul class="breadcrumb">

                <li>

                    <a href="">Home</a> <span class="divider">/</span>

                </li>

                <li>

                    <a href="#">Components</a> <span class="divider">/</span>

                </li>

                <li class="active">Form Styles</li>

            </ul>

        </div> <!-- /.page-title -->

        <div class="row">

            <div class="span8">

                <div id="horizontal" class="widget widget-form">

                    <div class="widget-header">

                        <h3>

                            <i class="icon-pencil"></i>

                            Horizontal Layout

                        </h3>

                    </div> <!-- /widget-header -->

                    <div class="widget-content">

                        <form class="form-horizontal">

                            <fieldset>

                                <div class="control-group">

                                    <label class="control-label" for="input01">Text input</label>

                                    <div class="controls">

                                        <input type="text" class="input-large" id="input01">

                                        <p class="help-block">In addition to freeform text, any HTML5 text-based input appears like so.</p>

                                    </div>

                                </div>

                                <div class="control-group">

                                    <label class="control-label" for="select01">Select list</label>

                                    <div class="controls">

                                        <select id="select01">

                                            <option>something</option>

                                            <option>2</option>

                                            <option>3</option>

                                            <option>4</option>

                                            <option>5</option>

                                        </select>

                                    </div>

                                </div>

                                <div class="control-group">

                                    <label class="control-label" for="multiSelect">Multicon-select</label>

                                    <div class="controls">

                                        <select multiple="multiple" id="multiSelect">

                                            <option>1</option>

                                            <option>2</option>

                                            <option>3</option>

                                            <option>4</option>

                                            <option>5</option>

                                        </select>

                                    </div>

                                </div>

                                <div class="control-group">

                                    <label class="control-label" for="fileInput">File input</label>

                                    <div class="controls">

                                        <input class="input-file" id="fileInput" type="file">

                                    </div>

                                </div>

                                <div class="control-group">

                                    <label class="control-label" for="textarea">Textarea</label>

                                    <div class="controls">

                                        <textarea class="input-large" id="textarea" rows="3"></textarea>

                                    </div>

                                </div>

                                <div class="control-group">

                                    <label class="control-label">Inline checkboxes</label>

                                    <div class="controls">

                                        <label class="checkbox inline">

                                            <input type="checkbox" id="inlineCheckbox1" value="option1"> 1

                                        </label>

                                        <label class="checkbox inline">

                                            <input type="checkbox" id="inlineCheckbox2" value="option2"> 2

                                        </label>

                                        <label class="checkbox inline">

                                            <input type="checkbox" id="inlineCheckbox3" value="option3"> 3

                                        </label>

                                    </div>

                                </div>

                                <div class="control-group">

                                    <label class="control-label">Checkboxes</label>

                                    <div class="controls">

                                        <label class="checkbox">

                                            <input type="checkbox" name="optionsCheckboxList1" value="option1">

                                            Option one is this and that-be sure to include why it's great

                                        </label>

                                        <label class="checkbox">

                                            <input type="checkbox" name="optionsCheckboxList2" value="option2">

                                            Option two can also be checked and included in form results

                                        </label>

                                    </div>

                                </div>

                                <div class="control-group">

                                    <label class="control-label">Radio buttons</label>

                                    <div class="controls">

                                        <label class="radio">

                                            <input type="radio" name="optionsRadios" id="optionsRadios1" value="option1" checked="">

                                            Option one is this and that-be sure to include why it's great

                                        </label>

                                        <label class="radio">

                                            <input type="radio" name="optionsRadios" id="optionsRadios2" value="option2">

                                            Option two can is something else and selecting it will deselect option one

                                        </label>

                                    </div>

                                </div>

                                <div class="form-actions">

                                    <button type="submit" class="btn btn-primary btn-large">Save changes</button>

                                    <button class="btn btn-large">Cancel</button>

                                </div>

                            </fieldset>

                        </form>

                    </div> <!-- /widget-content -->

                </div>

                <div id="" class="widget highlight widget-form">



                    <div class="widget-header">

                        <h3>

                            <i class="icon-pencil"></i>

                            Form Validation

                        </h3>

                    </div> <!-- /widget-header -->



                    <div class="widget-content">



                        <div class="alert alert-block">

                            <a class="close" data-dismiss="alert" href="#">&times;</a>

                            <h4 class="alert-heading">Validation Example!</h4>

                            Submit the below form to view the live form validation with integrated Bootstrap error messages.

                        </div>



                        <form  id="contact-form" class="form-horizontal" novalidate="novalidate">

                            <fieldset>

                                <div class="control-group">

                                    <label class="control-label" for="name">Your Name</label>

                                    <div class="controls">

                                        <input type="text" class="input-large" name="name" id="name">

                                    </div>

                                </div>

                                <div class="control-group">

                                    <label class="control-label" for="email">Email Address</label>

                                    <div class="controls">

                                        <input type="text" class="input-large" name="email" id="email">

                                    </div>

                                </div>

                                <div class="control-group">

                                    <label class="control-label" for="subject">Subject</label>

                                    <div class="controls">

                                        <input type="text" class="input-large" name="subject" id="subject">

                                    </div>

                                </div>

                                <div class="control-group">

                                    <label class="control-label" for="message">Your Message</label>

                                    <div class="controls">

                                        <textarea class="input-large" name="message" id="message" rows="3"></textarea>

                                    </div>

                                </div>



                                <div class="control-group">

                                    <label class="control-label" for="validateSelect">Select list</label>

                                    <div class="controls">

                                        <select id="validateSelect" name="validateSelect">

                                            <option value="">Select...</option>

                                            <option value="1">1</option>

                                            <option value="2">2</option>

                                            <option value="3">3</option>

                                            <option value="4">4</option>

                                            <option value="5">5</option>

                                        </select>

                                    </div>

                                </div>

                                <div class="control-group">

                                    <label class="control-label">Checkboxes</label>

                                    <div class="controls">

                                        <label class="checkbox">

                                            <input type="checkbox" name="validateCheckbox" value="option1">

                                            Option one is this and that-be sure to include why it's great

                                        </label>

                                        <label class="checkbox">

                                            <input type="checkbox" name="validateCheckbox" value="option2">

                                            Option two can also be checked and included in form results

                                        </label>

                                        <label class="checkbox">

                                            <input type="checkbox" name="validateCheckbox" value="option3">

                                            Option three can-yes, you guessed it-also be checked and included in form results

                                        </label>

                                        <p class="help-block"><strong>Note:</strong> Labels surround all the options for much larger click areas and a more usable form.</p>

                                    </div>

                                </div>

                                <div class="control-group">

                                    <label class="control-label">Radio buttons</label>

                                    <div class="controls">

                                        <label class="radio">

                                            <input type="radio" name="validateRadio" id="validateRadio1" value="option1">

                                            Option one is this and that-be sure to include why it's great

                                        </label>

                                        <label class="radio">

                                            <input type="radio" name="validateRadio" id="validateRadio2" value="option2">

                                            Option two can is something else and selecting it will deselect option one

                                        </label>

                                    </div>

                                </div>



                                <div class="form-actions">

                                    <button type="submit" class="btn btn-danger btn-large"><i class="icon-star"></i> Validate</button>

                                    <button type="reset" class="btn btn-large">Cancel</button>

                                </div>

                            </fieldset>

                        </form>



                    </div> <!-- /widget-content -->



                </div>









                <div id="appendprepend" class="widget widget-form">



                    <div class="widget-header">

                        <h3>

                            <i class="icon-pencil"></i>

                            Append/Prepend

                        </h3>

                    </div> <!-- /widget-header -->



                    <div class="widget-content">



                        <form class="form-horizontal">

                            <fieldset>



                                <div class="control-group">

                                    <label class="control-label" for="prependedInput">Prepended text</label>

                                    <div class="controls">

                                        <div class="input-prepend">

                                            <span class="add-on">@</span><input class="span2" id="prependedInput" size="16" type="text">

                                        </div>

                                        <p class="help-block">Here's some help text</p>

                                    </div>

                                </div>

                                <div class="control-group">

                                    <label class="control-label" for="appendedInput">Appended text</label>

                                    <div class="controls">

                                        <div class="input-append">

                                            <input class="span2" id="appendedInput" size="16" type="text"><span class="add-on">.00</span>

                                        </div>

                                        <span class="help-inline">Here's more help text</span>

                                    </div>

                                </div>

                                <div class="control-group">

                                    <label class="control-label" for="appendedPrependedInput">Append and prepend</label>

                                    <div class="controls">

                                        <div class="input-prepend input-append">

                                            <span class="add-on">$</span><input class="span2" id="appendedPrependedInput" size="16" type="text"><span class="add-on">.00</span>

                                        </div>

                                    </div>

                                </div>

                                <div class="control-group">

                                    <label class="control-label" for="appendedInputButton">Append with button</label>

                                    <div class="controls">

                                        <div class="input-append">

                                            <input class="span2" id="appendedInputButton" size="16" type="text"><button class="btn" type="button">Go!</button>

                                        </div>

                                    </div>

                                </div>

                                <div class="control-group">

                                    <label class="control-label" for="appendedInputButtons">Two-button append</label>

                                    <div class="controls">

                                        <div class="input-append">

                                            <input class="span2" id="appendedInputButtons" size="16" type="text"><button class="btn" type="button">Search</button><button class="btn" type="button">Options</button>

                                        </div>

                                    </div>

                                </div>



                                <div class="form-actions">

                                    <button type="submit" class="btn btn-primary btn-large">Save changes</button>

                                    <button class="btn btn-large">Cancel</button>

                                </div>

                            </fieldset>

                        </form>





                    </div> <!-- /widget-content -->



                </div> <!-- /.widget -->









                <div id="control" class="widget widget-form">



                    <div class="widget-header">

                        <h3>

                            <i class="icon-pencil"></i>

                            Control States

                        </h3>

                    </div> <!-- /widget-header -->



                    <div class="widget-content">



                        <form class="form-horizontal">

                            <fieldset>

                                <div class="control-group">

                                    <label class="control-label" for="focusedInput">Focused input</label>

                                    <div class="controls">

                                        <input class="input-large focused" id="focusedInput" type="text" value="This is focused...">

                                    </div>

                                </div>

                                <div class="control-group">

                                    <label class="control-label">Uneditable input</label>

                                    <div class="controls">

                                        <span class="input-large uneditable-input">Some value here</span>

                                    </div>

                                </div>

                                <div class="control-group">

                                    <label class="control-label" for="disabledInput">Disabled input</label>

                                    <div class="controls">

                                        <input class="input-large disabled" id="disabledInput" type="text" placeholder="Disabled input here..." disabled="">

                                    </div>

                                </div>

                                <div class="control-group">

                                    <label class="control-label" for="optionsCheckbox2">Disabled checkbox</label>

                                    <div class="controls">

                                        <label class="checkbox">

                                            <input type="checkbox" id="optionsCheckbox2" value="option1" disabled="">

                                            This is a disabled checkbox

                                        </label>

                                    </div>

                                </div>

                                <div class="control-group warning">

                                    <label class="control-label" for="inputWarning">Input with warning</label>

                                    <div class="controls">

                                        <input type="text" id="inputWarning">

                                        <span class="help-inline">Something may have gone wrong</span>

                                    </div>

                                </div>

                                <div class="control-group error">

                                    <label class="control-label" for="inputError">Input with error</label>

                                    <div class="controls">

                                        <input type="text" id="inputError">

                                        <span class="help-inline">Please correct the error</span>

                                    </div>

                                </div>

                                <div class="control-group success">

                                    <label class="control-label" for="inputSuccess">Input with success</label>

                                    <div class="controls">

                                        <input type="text" id="inputSuccess">

                                        <span class="help-inline">Woohoo!</span>

                                    </div>

                                </div>

                                <div class="control-group success">

                                    <label class="control-label" for="selectError">Select with success</label>

                                    <div class="controls">

                                        <select id="selectError">

                                            <option>1</option>

                                            <option>2</option>

                                            <option>3</option>

                                            <option>4</option>

                                            <option>5</option>

                                        </select>

                                        <span class="help-inline">Woohoo!</span>

                                    </div>

                                </div>

                                <div class="form-actions">

                                    <button type="submit" class="btn btn-primary btn-large">Save changes</button>

                                    <button class="btn btn-large">Cancel</button>

                                </div>

                            </fieldset>

                        </form>



                    </div> <!-- /widget-content -->



                </div> <!-- /.widget -->



            </div> <!-- /span8 -->





            <div class="span4">



                <div id="formToc" class="widget highlight">



                    <div class="widget-header">

                        <h3>Flexible HTML and CSS</h3>

                    </div> <!-- /widget-header -->



                    <div class="widget-content">

                        <p>The best part about forms in Bootstrap is that all your inputs and controls look great no matter how you build them in your markup. No superfluous HTML is required, but we provide the patterns for those who require it.</p>



                        <br>



                        <p>Bootstrap's forms include styles for all the base form controls like input, textarea, and select you'd expect. But it also comes with a number of custom components like appended and prepended inputs and support for lists of checkboxes.</p>





                    </div> <!-- /widget-content -->



                </div> <!-- /widget -->









            </div> <!-- /.span4 -->







        </div> <!-- /row -->









    </div> <!-- /.container -->



</div> <!-- /#content -->







<div id="footer">



    <div class="container">



        &copy; 2012 Propel UI, all rights reserved.



    </div> <!-- /.container -->



</div> <!-- /#footer -->











</body>

</html>


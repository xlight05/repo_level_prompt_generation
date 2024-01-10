(function($) {
	$.extend({
		layouter: new function() {

//			this.defaults = {
//				increment	: 2,
//				speed		: 15,
//				showText	: true,											// show text with percentage in next to the progressbar? - default : true
//				width		: 120,											// Width of the progressbar - don't forget to adjust your image too!!!
//				boxImage	: 'images/progressbar.gif',		// boxImage : image around the progress bar
//				barImage	: 'images/progressbg_green.gif',	// Image to use in the progressbar. Can be an array of images too.
//				height		: 12											// Height of the progressbar - don't forget to adjust your image too!!!
//			};
			
			/* public methods */
			this.resizeLayoutIntern = function() {
				$("div.left").height($(document).height() 
										- $('#north').height() 
										- $('#messages').height() 
										- $('#footer').height() 
										- 20);
				
				$("div.mybody").height($(document).height() 
										- $('#north').height() 
										- $('#messages').height() 
										- $('#footer').height() 
										- 22);
			}
			
			this.resizeLayoutWithTabIntern = function() {
				$("table.pageTabPanel").height($(document).height() 
						- $('#north').height() 
						- $('#messages').height() 
						- $('#footer').height() 
						- 20);
				
				$("div.left").height($(document).height() 
						- $('#north').height() 
						- $('#messages').height() 
						- $('#footer').height() 
						- 69);

				$("div.mybody").height($(document).height() 
						- $('#north').height() 
						- $('#messages').height() 
						- $('#footer').height() 
						- 71);
			}
		}
	});
	
	
	$.fn.extend({
        resizeLayout: $.layouter.resizeLayoutIntern,
        resizeLayoutWithTab: $.layouter.resizeLayoutWithTabIntern
	});
	
})(jQuery);
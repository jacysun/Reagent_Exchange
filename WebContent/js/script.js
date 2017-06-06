$(function() {
var instituteList = [ "Johns Hopskins University",
                    "Duke University",
                    "Massachusetts Institute of Technology",
                    "Worcester Polytechnic Institute",
                    "University of Massachusetts Medical School",
                    "Stanford University",
                    "Harvard University"];
		
		    $( "#institute" ).autocomplete({
				source: instituteList
		    });
		  });

$(function() {
	var labList = [ "Mercurio Lab",
	                    "Shaw Lab",
	                    "Cantor Lab",
	                    "Mao Lab",
	                    "Green Lab",
	                    "Lewis Lab",
	                    "Wang Lab",
	                    "Kelliher Lab"];
			
			    $( "#lname" ).autocomplete({
					source: labList
			    });
			  });

$(function() {
	var reagentList = [ "Lysis Buffer",
	                    "PI3K antibody",
	                    "Integrin b4 antibody",
	                    "PCDH-GFP"];
			
			    $( "#rname" ).autocomplete({
					source: reagentList
			    });
			  });

$(function() {
	var typeList = [ "antibody",
	                    "plasmid",
	                    "kit",
	                    "buffer"];
			
			    $( "#rtype" ).autocomplete({
					source: typeList
			    });
			  });

$(function() {
	var areaList = [ "Cancer Biology",
	                    "Cell Biology",
	                    "Immunology",
	                    "Neuroscience",
	                    "Microbiology",
	                    "Biomedical Engineering"];
			
			    $( "#rsarea" ).autocomplete({
					source: areaList
			    });
			  });
<#include "header.ftl">

	<#include "menu.ftl">

	<div class="page-header">
		<h1><#escape x as x?xml>${content.title}</#escape></h1>
	</div>

	<p><em>${content.date?string("dd MMMM yyyy")}</em></p>

	<p>${content.body}</p>

	<hr />


	<section>
		<h1>Comments</h1>

		<div id="disqus_thread" aria-live="polite"><noscript>Please enable JavaScript to view the <a href="http://disqus.com/?ref_noscript">comments powered by Disqus.</a></noscript>
		</div>
	</section>

    <#if config.disqus_enabled?? && config.disqus_enabled == "true">
	    <script type="text/javascript">
	      /* * * CONFIGURATION VARIABLES: EDIT BEFORE PASTING INTO YOUR WEBPAGE * * */
	      var disqus_shortname = '${config.disqus_username}'; // required: replace example with your forum shortname
		  var disqus_script = 'embed.js';

		  // var disqus_identifier = 'http://blog.mooregreatsoftware.com/2015/08/18/aem-6-dot-1-package-manager-metadata-files/';

	  	  // var disqus_url = 'http://blog.mooregreatsoftware.com/2015/08/18/aem-6-dot-1-package-manager-metadata-files/';

	      /* * * DON'T EDIT BELOW THIS LINE * * */
	      (function () {
	        var s = document.createElement('script');
			s.async = true;
	        s.type = 'text/javascript';
	        s.src = '//' + disqus_shortname + '.disqus.com/' + disqus_script;
	        (document.getElementsByTagName('HEAD')[0] || document.getElementsByTagName('BODY')[0]).appendChild(s);
	      }());
	    </script>
    </#if>

<#include "footer.ftl">

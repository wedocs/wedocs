<#include "header.ftl">

	<#include "menu.ftl">

	<div class="page-header">
		<h1><#escape x as x?xml>${content.title}</#escape></h1>
	</div>
	<hr />
	<section class="documentation">
		<div class="container">
			<div class="row">
				<div class="col-md-3">
                    <em>${content.date?string("dd MMMM yyyy")}</em>
				</div>
				<div class="col-md-9 content">
					${content.body}
				</div>
			</div>
		</div>
	</section>
<#include "footer.ftl">

<%
	DIRECTIVE SAFE_OUTPUT_OPEN;
%>

<script>
 function preSubmit(pageNum) {
 	var pageSize = document.getElementById("selectedPageSize").value;
 	submitPage(pageNum, pageSize);
 }
</script>

<div class="am-g">

	<div class="am-u-md-5 am-u-sm-12">
			Total: ${page.totalCount} entries.
	</div>
	<div class="am-u-md-7 am-u-sm-12">
			<ul data-am-widget="pagination" class="am-pagination am-pagination-default">
				<%
					if (page.totalPageNum > 1 && page.currentPageNum > 1) {
				%>
				<li class="am-pagination-prev "><a
					href="javascript:preSubmit('${page.prePageNum}')">
						上一页 </a></li>
				<%
					}
				%>

				<%
					var maxHrefNum = 10;
					var end = (page.currentPageNum + maxHrefNum/2) > page.totalPageNum ? 
						page.totalPageNum : (page.currentPageNum + maxHrefNum/2);
					var hrefNum = page.totalPageNum < maxHrefNum ? page.totalPageNum : maxHrefNum;
					var begin = end - hrefNum + 1;
					if (begin < 1) {
						begin = 1;
						end = begin + hrefNum - 1;
					}
					while (begin <= end) {
						if (begin == page.currentPageNum) {
				%>
				<li class="am-active"><a class="am-active"
					href="javascript:preSubmit(${begin})">${begin}</a>
					<%
						} else {
					%>
				
				<li><a
					href="javascript:preSubmit(${begin})">${begin}</a>
					<%
						}
							begin++;
						}
					%> <%
 	if (page.currentPageNum < page.totalPageNum) {
 %>
				
				<li class="next"><a
					href="javascript:preSubmit('${page.nextPageNum}')">
						下一页 </a></li>
				<%
					}
				%>
			</ul>
		
	</div>


</div>

<%
	//关闭安全输出。
	DIRECTIVE SAFE_OUTPUT_CLOSE;
%>
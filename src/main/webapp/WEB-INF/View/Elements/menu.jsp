<nav>
	<c:url var="root" value="/" />
	
	<ol>
		<li><jsp:forward page="Role/index.html"></jsp:forward></li>
		<li><a href="${root}listar?tabela=cor">Cor</a></li>
		<li><a href="${root}listar?tabela=escolaridade">Escolaridade</a></li>
		<li><a href="${root}listar?tabela=especialidade">Especialidade</a></li>
		<li><a href="${root}listar?tabela=estadocivil">Estado civil</a></li>
		<li><a href="${root}listar?tabela=exame">Exame</a></li>
		<li><a href="${root}listar?tabela=plano">Plano de saude</a></li>
		<li><a href="${root}listar?tabela=sala">Sala</a></li>		
		<li><a href="${root}pessoafisica">Pessoa Física</a></li>
	</ol>
</nav>
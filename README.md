# tec502-pbl1-monitoring

<p align="center">
  <img src="https://i.imgur.com/LNznKFU.png" alt="Monitoring" width="650px" height="450px">
</p>

## 📖 Descrição do Projeto ##
> **Resolução do problema 1 do MI - Concorrência e Conectividade (TEC 502).**<br/><br/>
O projeto trata-se de um dos *clients* que se conecta ao [servidor](https://github.com/AllanCapistrano/tec502-pbl1-server) principal, e tem como função exibir os dados, que são medidos pelos sensores e processados no servidor, para os médicos poderem acompanhar a situação em tempo real dos pacientes que estão utilizando o dispositivo de monitoramento de COVID-19.<br/>
Este *client* se comunica com o servidor utilizando um protocolo baseado em [API REST](https://www.redhat.com/pt-br/topics/api/what-is-a-rest-api), com requisições [HTTP](https://developer.mozilla.org/pt-BR/docs/Web/HTTP/Methods) e mensagens no formato [JSON](https://www.json.org/json-en.html). Essas requisições são feitas para o servidor a cada 5 segundos por uma *thread*, garantindo dessa forma, um acompanhamento em tempo real dos dados por um profissional da saúde.

### ⛵ Navegação pelos projetos: ###
- [Servidor](https://github.com/AllanCapistrano/tec502-pbl1-server)
- [Emulador de Sensores](https://github.com/AllanCapistrano/tec502-pbl1-sensors)
- \> Monitoramento de Pacientes

### 📂 Tecnologias utilizadas: ### 
- [Java JDK 8](https://www.oracle.com/br/java/technologies/javase/javase-jdk8-downloads.html)
- [Scene Builder](https://gluonhq.com/products/scene-builder/)

### 📦 Dependências: ### 
- [JSON](https://www.json.org/json-en.html)

------------

## 💻 Como utilizar ##
1. Caso esteja utilizando o sistema operacional **Windows**, [clique aqui](https://github.com/AllanCapistrano/tec502-pbl1-monitoring/releases/tag/v1.0) e faça o download do arquivo `.exe` ou `.jar`;
2. Porém, caso esteja utilizando o sistema operacional **macOS** ou alguma distribuição **Linux**, [clique aqui](https://github.com/AllanCapistrano/tec502-pbl1-monitoring/releases/tag/v1.0) e faça o download do arquivo `.jar`;
3. Após isso, com o [servidor](https://github.com/AllanCapistrano/tec502-pbl1-server) *online*, basta instalar o arquivo `.exe` ou executar o arquivo `.jar`.

------------

## 👨‍💻 Autor ##

| [![Allan Capistrano](https://github.com/AllanCapistrano.png?size=100)](https://github.com/AllanCapistrano) |
| -----------------------------------------------------------------------------------------------------------|
| [Allan Capistrano](https://github.com/AllanCapistrano)                                                     |

<p>
    <h3>Onde me encontrar:</h3>
    <a href="https://github.com/AllanCapistrano">
        <img src="https://github.com/AllanCapistrano/AllanCapistrano/blob/master/assets/github-square-brands.png" alt="Github icon" width="5%">
    </a>
    &nbsp
    <a href="https://www.linkedin.com/in/allancapistrano/">
        <img src="https://github.com/AllanCapistrano/AllanCapistrano/blob/master/assets/linkedin-brands.png" alt="Linkedin icon" width="5%">
    </a> 
    &nbsp
    <a href="https://mail.google.com/mail/u/0/?view=cm&fs=1&tf=1&source=mailto&to=asantos@ecomp.uefs.br">
        <img src="https://github.com/AllanCapistrano/AllanCapistrano/blob/master/assets/envelope-square-solid.png" alt="Email icon" width="5%">
    </a>
</p>

------------

## ⚖️ Licença ##
[GPL-3.0 License](https://github.com/AllanCapistrano/tec502-pbl1-monitoring/blob/main/LICENSE)

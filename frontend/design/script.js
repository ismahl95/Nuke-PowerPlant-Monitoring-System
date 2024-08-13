document.getElementById('toggle-menu').addEventListener('click', function () {
  console.log('Botón clicado'); // Verificar si el evento de clic se registra
  const container = document.querySelector('.layout-content-container');
  container.classList.toggle('collapsed');
  console.log('Clase "collapsed" aplicada:', container.classList.contains('collapsed')); // Verificar si la clase se aplica
});
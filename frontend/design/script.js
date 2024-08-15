document.addEventListener('DOMContentLoaded', function() {
  const menu = document.querySelector('.bg-gray-200');

  menu.addEventListener('mouseover', function() {
      this.classList.remove('collapsed');
  });

  menu.addEventListener('mouseout', function() {
      this.classList.add('collapsed');
  });
});

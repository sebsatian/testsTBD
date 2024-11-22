import { createRouter, createWebHistory } from 'vue-router';
import MainPage from '../components/MainPage.vue';
import RegisterForm from '../components/RegisterForm.vue';
import LoginForm from '../components/LoginForm.vue';
import ClientPage from '../components/ClientPage.vue';
import ViewTasks from '../components/ViewTasks.vue';
import ViewNotifications from '../components/ViewNotifications.vue';
import CreateTasks from '../components/CreateTasks.vue';
import TaskDetails from '../components/TaskDetails.vue'; // Importa el componente de detalles de tarea
import EditTask from '../components/EditTask.vue'; // Importa el componente de edición de tarea
import CompleteRegister from '../components/CompleteRegister.vue';

const routes = [
  {
    path: '/',
    name: 'MainPage',
    component: MainPage,
  },
  {
    path: '/register',
    name: 'Register',
    component: RegisterForm,
  },
  {
    path: '/login',
    name: 'Login',
    component: LoginForm,
  },
  {
    path: '/clientpage',
    name: 'ClientPage',
    component: ClientPage,
  },
  {
    path: '/clientpage/tasks',
    name: 'ViewTasks',
    component: ViewTasks,
  },
  {
    path: '/clientpage/tasks/create',
    name: 'CreateTask',
    component: CreateTasks,
  },
  {
    path: '/clientpage/notifications',
    name: 'ViewNotifications',
    component: ViewNotifications,
  },
  {
    path: '/clientpage/tasks/:id', // Ruta para los detalles de una tarea
    name: 'TaskDetails',
    component: TaskDetails,
    props: true, // Pasa el parámetro `id` como prop al componente
  },

  {
    path: '/clientpage/tasks/:id/edit', 
    name: 'EditTask',
    component: EditTask,
    props: true, 
  },

  {
    path: '/complete-register',
    name: 'CompleteRegister',
    component: CompleteRegister,
  },
  

];

const router = createRouter({
  history: createWebHistory('/'),
  routes,
});

export default router;

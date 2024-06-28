import { router } from './router.js';
import { Chat } from './components/chat.js';
// import { service } from './service.js'; // Ex.4 verification

router.register('/', Chat);

/* Ex. 4 verification */
// console.log("main.js loaded"); // Initial log to verify main.js is loaded

// service.getTopics()
//     .then(topics => {
//         console.log('Topics fetched successfully:', topics); // Success log
//     })
//     .catch(error => {
//         console.error('Failed to fetch topics:', error); // Error log
//     });

// service.getMessages('Java')
//     .then(messages => {
//         console.log('Messages for topic "Java" fetched successfully:', messages); // Success log
//     })
//     .catch(error => {
//         console.error('Failed to fetch messages:', error); // Error log
//     });

router.navigate('/');
